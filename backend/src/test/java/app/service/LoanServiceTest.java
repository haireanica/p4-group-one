package app.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.web.server.ResponseStatusException;

import app.domain.Asset;
import app.domain.Loan;
import app.domain.Loanee;
import app.domain.assetStatus;
import app.domain.loanStats;
import app.repository.AssetRepository;
import app.repository.LoanRepository;
import app.repository.LoaneeRepository;
import app.repository.MaintenanceRepository;

public class LoanServiceTest {

    private LoanRepository loanRepository;
    private AssetRepository assetRepository;
    private LoaneeRepository loaneeRepository;
    private MaintenanceManager maintenanceManager;

    private LoanService loanService;

    @BeforeEach
    void setUp() {
        loanRepository = mock(LoanRepository.class);
        assetRepository = mock(AssetRepository.class);
        loaneeRepository = mock(LoaneeRepository.class);
        maintenanceManager = mock(MaintenanceManager.class);

        loanService = new LoanService(
                loanRepository,
                assetRepository,
                loaneeRepository,
                maintenanceManager
        );
    }

    @Test
    void checkoutSuccess() {
        Asset asset = new Asset(
                "Lap100",
                "ThinkPad 3",
                assetStatus.AVAILABLE,
                List.of(),
                "Laptop"
        );

        Loanee loanee = mock(Loanee.class);

        when(assetRepository.findById("Lap100"))
                .thenReturn(Optional.of(asset));

        when(loaneeRepository.findById(100))
                .thenReturn(Optional.of(loanee));

        when(loanRepository.save(any(Loan.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Loan result = loanService.checkout(
                "Lap100",
                100,
                "Coursework",
                "Education",
                14
        );

        assertNotNull(result);
        assertEquals(loanStats.CHECKED_OUT, result.getLoanStatus());
        assertEquals(assetStatus.CHECKED_OUT, asset.getStatus());
        assertEquals(LocalDate.now().plusDays(14), result.getDueDate());

        verify(assetRepository).save(asset);
        verify(loanRepository).save(any(Loan.class));
    }

    @Test
    void checkoutRejectUnavailableAsset() {
        Asset asset = new Asset(
                "Lap100",
                "ThinkPad 3",
                assetStatus.CHECKED_OUT,
                List.of(),
                "Laptop"
        );

        when(assetRepository.findById("Lap100"))
                .thenReturn(Optional.of(asset));

        assertThrows(
                ResponseStatusException.class,
                () -> loanService.checkout(
                        "Lap100",
                        100,
                        "Coursework",
                        "Education",
                        14
                )
        );

        verify(assetRepository, never()).save(any(Asset.class));
        verify(loanRepository, never()).save(any(Loan.class));
    }

    @Test
    void checkoutRejectsMissingAsset() {
        when(assetRepository.findById("Lap100"))
                .thenReturn(Optional.empty());

        assertThrows(
                ResponseStatusException.class,
                () -> loanService.checkout(
                        "Lap100",
                        100,
                        "Coursework",
                        "Education",
                        14
                )
        );

        verify(assetRepository, never()).save(any(Asset.class));
        verify(loanRepository, never()).save(any(Loan.class));
    }

    @Test
    void checkoutRejectsMissingResident() {
        Asset asset = new Asset(
                "Lap100",
                "ThinkPad 3",
                assetStatus.AVAILABLE,
                List.of(),
                "Laptop"
        );

        when(assetRepository.findById("Lap100"))
                .thenReturn(Optional.of(asset));
        when(loaneeRepository.findById(999))
                .thenReturn(Optional.empty());

        assertThrows(
                ResponseStatusException.class,
                () -> loanService.checkout(
                        "Lap100",
                        999,
                        "Coursework",
                        "Education",
                        14
                )
        );

        verify(assetRepository, never()).save(any(Asset.class));
        assertEquals(assetStatus.AVAILABLE, asset.getStatus());
    }

    @Test
    void returnLoanSuccess() {
        Asset asset = new Asset(
                "Lap100",
                "ThinkPad 3",
                assetStatus.CHECKED_OUT,
                List.of(),
                "Laptop"
        );

        Loanee loanee = mock(Loanee.class);

        Loan loan = new Loan(
                LocalDate.now().minusDays(5),
                LocalDate.now().plusDays(9),
                null,
                asset,
                loanStats.CHECKED_OUT,
                loanee,
                "Education coursework",
                "Education"
        );

        when(loanRepository.findById(1))
                .thenReturn(Optional.of(loan));
        when(loanRepository.save(any(Loan.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Loan result = loanService.returnLoan(1, "good", "");

        assertEquals(loanStats.CHECKED_IN, result.getLoanStatus());
        assertEquals(LocalDate.now(), result.getReturnDate());
        assertEquals(assetStatus.AVAILABLE, asset.getStatus());

        verify(assetRepository).save(asset);
        verify(loanRepository).save(loan);
    }

    @Test
    void returnLoanRejectsDuplicateReturn() {
        Asset asset = new Asset(
                "Lap100",
                "ThinkPad 3",
                assetStatus.AVAILABLE,
                List.of(),
                "Laptop"
        );

        Loanee loanee = mock(Loanee.class);

        Loan loan = new Loan(
                LocalDate.now().minusDays(5),
                LocalDate.now().plusDays(9),
                LocalDate.now(),
                asset,
                loanStats.CHECKED_IN,
                loanee,
                "Education coursework",
                "Education"
        );

        when(loanRepository.findById(1))
                .thenReturn(Optional.of(loan));

        assertThrows(
                ResponseStatusException.class,
                () -> loanService.returnLoan(1, "good", "")
        );

        verify(assetRepository, never()).save(any(Asset.class));
        verify(loanRepository, never()).save(any(Loan.class));
    }
}