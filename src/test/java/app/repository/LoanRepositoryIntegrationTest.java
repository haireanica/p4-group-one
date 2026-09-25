package app.repository;

import app.domain.Asset;
import app.domain.Center;
import app.domain.Inventory;
import app.domain.Loan;
import app.domain.Maintenance;
import app.domain.Loanee;
import app.domain.assetStatus;
import app.domain.loanStats;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class LoanRepositoryIntegrationTest {

    @Autowired
    private CenterRepository centerRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private AssetRepository assetRepository;

    @Autowired
    private LoaneeRepository loaneeRepository;

    @Autowired
    private LoanRepository loanRepository;

    @Test
    void shouldSaveAndRetrieveLoanWithAssetAndLoanee() {
        // 1. Create and save center
        Center center = new Center(
                "Loan Test Center",
                "123 Test Street",
                null,
                "Orlando",
                "FL",
                "32801"
        );

        center = centerRepository.saveAndFlush(center);

        // crate and save inventory
        Inventory inventory = new Inventory(center);

        inventory = inventoryRepository.saveAndFlush(inventory);

        // 3. Create and save Asset
        Asset asset = new Asset(
                "TEST-LOAN-ASSET-001",
                "Test Laptop",
                assetStatus.AVAILABLE,
                new ArrayList<Maintenance>()
        );




        asset.setInventory(inventory);

        asset = assetRepository.saveAndFlush(asset);

        // 4. Create and save Loanee
        Loanee loanee = new Loanee(900001);

        loanee = loaneeRepository.saveAndFlush(loanee);

        // 5. Create Loan
        LocalDate checkoutDate = LocalDate.now();
        LocalDate dueDate = checkoutDate.plusDays(14);

        Loan loan = new  Loan(
                checkoutDate,
                dueDate,
                null,
                asset,
                loanStats.READY,
                loanee
        );

        loan = loanRepository.saveAndFlush(loan);

        // 6. Verify database generated the Laon ID
        assertNotNull(loan.getLoanId());

        // 7. Retrieve Loan from MySQL
        Loan savedLoan = loanRepository
                .findById(loan.getLoanId())
                        .orElseThrow();

        // 8. Verify Loan data
        assertEquals(checkoutDate, savedLoan.getCheckoutDate());
        assertEquals(dueDate, savedLoan.getDueDate());
        assertEquals(loanStats.READY, savedLoan.getLoanStatus());

        // 9. Verify Asset relationship
        assertNotNull(savedLoan.getAssetId());
        assertEquals(
                "TEST-LOAN-Asset-001",
                savedLoan.getAssetId().getAssetId()
        );

        // Verify Loanee relationship
        assertNotNull(savedLoan.getParticipantId());

        assertEquals(
                900001,
                savedLoan.getParticipantId().getParticipantId()
        );


    }
}
