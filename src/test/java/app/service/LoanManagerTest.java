package app.service;

import app.domain.*;

import app.repository.AssetRepository;
import app.repository.LoaneeRepository;
import app.repository.LoanRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LoanManagerTest {

    private LoanRepository loanRepository;
    private AssetRepository assetRepository;
    private LoaneeRepository loaneeRepository;

    private LoanManager manager;

    @BeforeEach
    void setUp() {
        loanRepository = mock(LoanRepository.class);
        assetRepository = mock(AssetRepository.class);
        loaneeRepository = mock(LoaneeRepository.class);

        manager = new LoanManager(
                loanRepository,
                assetRepository,
                loaneeRepository
        );
    }

    @Test
    void checkoutSuccess() {
        // Arrange: Create an available asset
        Asset asset = new Asset(
                "Lap100",
                "ThinkPad 3",
                assetStatus.AVAILABLE,
                List.of()
        );

        // Create a mock resident
        Loanee loanee = mock(Loanee.class);

        LocalDate dueDate = LocalDate.now().plusDays(14);

        // Simulate finding the asset
        when(assetRepository.findById("Lap100"))
                .thenReturn(Optional.of(asset));

        //Simulate finding the resident
        when(loaneeRepository.findById(100))
                .thenReturn(Optional.of(loanee));

        //Simulate saving the loan
        when(loanRepository.save(any(Loan.class)))
                .thenAnswer(invocation ->
                        invocation.getArgument(0));

        // Act: Perform checkout
        Loan result = manager.checkout(
                "Lap100",
                100,
                dueDate
        );

        // Assert: Verify checkout results
        assertNotNull(result);

        assertEquals(
                loanStats.CHECKED_OUT,
                result.getLoanStatus()
        );

        assertEquals(
                assetStatus.CHECKED_OUT,
                asset.getStatus()
        );

        assertEquals(
                dueDate,
                result.getDueDate()
        );

        //Verify database operations were requested
        verify(assetRepository).save(asset);

        verify(loanRepository).save(any(Loan.class));
    }

    @Test
    void checkoutRejectUnavailableAsset() {
        //Arrange
        Asset asset = new Asset(
                "Lap100",
                "ThinkPad 3",
                assetStatus.CHECKED_OUT,
                List.of()
        );

        when(assetRepository.findById("Lap100"))
                .thenReturn(Optional.of(asset));

        // Act and Assert
        assertThrows(
                IllegalStateException.class,
                () -> manager.checkout(
                        "Lap100",
                        100,
                        LocalDate.now().plusDays(14)
                )
        );

        // Verify no changes were saved
        verify(assetRepository, never())
                .save(any(Asset.class));

        verify(loanRepository, never())
                .save(any(Loan.class));

    }

    @Test
    void checkoutRejectsMissingAsset() {
        //Arrange: Asset does not exist
        when(assetRepository.findById("Lap100"))
                .thenReturn(Optional.empty());

        //ACt and Assert
        assertThrows(
                IllegalArgumentException.class,
                () -> manager.checkout(
                        "Lap999",
                        100,
                        LocalDate.now().plusDays(14)
                )
        );

        //Verify no records were saved
        verify(assetRepository, never())
                .save(any(Asset.class));

        verify(loanRepository, never())
                .save(any(Loan.class));
    }
    @Test
    void checkoutRejectsMissingResident() {

        //Arrange: Asset exists and is available
        Asset asset = new Asset(
                "Lap100",
                "ThinkPad 3",
                assetStatus.AVAILABLE,
                List.of()
        );

        when(assetRepository.findById("Lap100"))
                .thenReturn(Optional.of(asset));

        // Resident does not exist
        when(loaneeRepository.findById(999))
                .thenReturn(Optional.empty());

        // Acr and Assert
        assertThrows(
                IllegalArgumentException.class,
                () -> manager.checkout(
                        "Lap100",
                        999,
                        LocalDate.now().plusDays(14)
                )
        );

        // Verify no databases changes
        verify(assetRepository, never())
                .save(any(Asset.class));

        // Asset should remain available
        assertEquals(
                assetStatus.AVAILABLE,
                asset.getStatus()
        );
    }

    @Test
    void checkoutRejectsInvalidDueDate() {

        //Arrange: Valid asset and resident
        Asset asset = new Asset(
                "Lap100",
                "ThinkPad 3",
                assetStatus.AVAILABLE,
                null
        );

        Loanee loanee = mock(Loanee.class);

        when(assetRepository.findById("Lap100"))
                .thenReturn(Optional.of(asset));

        when(loaneeRepository.findById(100))
                .thenReturn(Optional.of(loanee));

        // Act and Assert: Reject a past due date
        assertThrows(
                IllegalArgumentException.class,
                () -> manager.checkout(
                        "Lap100",
                        100,
                        LocalDate.now().minusDays(1)
                )
        );

        // Verify no records were saved
        verify(assetRepository, never())
                .save(any(Asset.class));

        verify(loanRepository, never())
                .save(any(Loan.class));

        // Asset must remain available
        assertEquals(
                assetStatus.AVAILABLE,
                asset.getStatus()
        );
    }

    @Test
    void checkoutRejectsNullDueDate() {

        //Arrange: Create an available asset
        Asset asset =  new Asset("Lap100", "ThinkPad 3", assetStatus.AVAILABLE, null);

        // Acr and Assert: Reject a missing due date
        assertThrows(
                IllegalArgumentException.class,
                () -> manager.checkout(
                        "Lap100",
                        100,
                        null
                )
        );

        // Verify: No asset changes were saved
        verify(assetRepository, never())
                .save(any(Asset.class));

        // Verify: Asset remains available
        assertEquals(
                assetStatus.AVAILABLE,
                asset.getStatus()
        );
    }

    @Test
    void returnAssetSuccess() {
        //Arrange: Create an asset taht is checked out
        Asset asset = new Asset(
                "Lap100",
                "ThinkPad 3",
                assetStatus.CHECKED_OUT,
                List.of()
        );

        // Create a resident
        Loanee loanee = mock(Loanee.class);

        // Create an active loan
        Loan loan = new Loan(
                LocalDate.now().minusDays(5),
                LocalDate.now().plusDays(9),
                null,
                asset,
                loanStats.CHECKED_OUT,
                loanee
        );

        //Simulate finding the existing loan
        when(loanRepository.findById(1))
                .thenReturn(Optional.of(loan));

        // Simulate saving the updated loan
        when(loanRepository.save(any(Loan.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Act: return the asset
        Loan result = manager.returnAsset(1);

        // Assert: loan is marked as checked in
        assertEquals(
                loanStats.CHECKED_IN,
                result.getLoanStatus()
        );

        // Assert: Return date is recorded
        assertEquals(
                LocalDate.now(),
                result.getReturnDate()
        );

        //Verify both database saves were requested
        verify(assetRepository).save(asset);
        verify(loanRepository).save(loan);


    }


    @Test
    void returnAssetRejectsDuplicateReturn() {
        // Arrange: create an asset that has already been returned
        Asset asset = new Asset(
                "Lap100",
                "ThinkPad 3",
                assetStatus.AVAILABLE,
                List.of()
        );

        Loanee loanee = mock(Loanee.class);

        Loan loan = new Loan(
                LocalDate.now().minusDays(5),
                LocalDate.now().plusDays(9),
                LocalDate.now(),
                asset,
                loanStats.CHECKED_IN,
                loanee
        );

        when(loanRepository.findById(1))
                .thenReturn(Optional.of(loan));

        //Act and assert: Reject duplicate return
        assertThrows(
                IllegalStateException.class,
                () -> manager.returnAsset(1)
        );

        //Verify no database updates were requested
        verify(assetRepository, never())
                .save(any(Asset.class));

        verify(loanRepository, never())
                .save(any(Loan.class));

        //Verify original state remains unchanged
        assertEquals(
                loanStats.CHECKED_IN,
                loan.getLoanStatus()
        );

        assertEquals(
                assetStatus.AVAILABLE,
                asset.getStatus()
        );
    }


}
