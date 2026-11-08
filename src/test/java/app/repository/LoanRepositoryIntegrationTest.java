package app.repository;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import app.domain.Asset;
import app.domain.Inventory;
import app.domain.Loan;
import app.domain.Loanee;
import app.domain.Maintenance;
import app.domain.assetStatus;
import app.domain.loanStats;
import jakarta.persistence.EntityManager;

@SpringBootTest
@Transactional
public class LoanRepositoryIntegrationTest {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private AssetRepository assetRepository;

    @Autowired
    private LoaneeRepository loaneeRepository;

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void shouldSaveAndRetrieveLoanWithAssetAndLoanee() {
        int centerId = 998877;
        int inventoryId = 554433;
        int participantId = 900001;

        // Use native SQL to insert Center with manual ID
        entityManager.createNativeQuery(
                        "INSERT INTO center (centerId, name, address_line1, city, state, postal_code) VALUES (?, ?, ?, ?, ?, ?)")
                .setParameter(1, centerId)
                .setParameter(2, "Loan Test Center")
                .setParameter(3, "123 Test Street")
                .setParameter(4, "Orlando")
                .setParameter(5, "FL")
                .setParameter(6, "32801")
                .executeUpdate();

        // Use native SQL to insert Inventory
        entityManager.createNativeQuery(
                        "INSERT INTO inventory (inventoryId, centerId) VALUES (?, ?)")
                .setParameter(1, inventoryId)
                .setParameter(2, centerId)
                .executeUpdate();

        // Use native SQL to insert Loanee with manual ID, avoiding JPA merge/detached state conflicts
        entityManager.createNativeQuery(
                        "INSERT INTO loanee (participantId, firstName, lastName, phoneNumber, address_line1, city, state, postal_code) VALUES (?, ?, ?, ?, ?, ?, ?, ?)")
                .setParameter(1, participantId)
                .setParameter(2, "John")
                .setParameter(3, "Doe")
                .setParameter(4, "555-0100")
                .setParameter(5, "456 Loanee Ave")
                .setParameter(6, "Orlando")
                .setParameter(7, "FL")
                .setParameter(8, "32801")
                .executeUpdate();

        entityManager.clear();

        Inventory inventory = inventoryRepository.findById(inventoryId).orElseThrow();
        Loanee loanee = loaneeRepository.findById(participantId).orElseThrow();

        Asset asset = new Asset(
                "TEST-LOAN-Asset-001",
                "Test Laptop",
                assetStatus.AVAILABLE,
                new ArrayList<Maintenance>(),
                "Laptop"
        );

        asset.setInventory(inventory);
        asset = assetRepository.saveAndFlush(asset);

        LocalDate checkoutDate = LocalDate.now();
        LocalDate dueDate = checkoutDate.plusDays(14);

        Loan loan = new Loan(
                checkoutDate,
                dueDate,
                null,
                asset,
                loanStats.READY,
                loanee,
                "Needed for coursework",
                "Education"
        );

        loan = loanRepository.saveAndFlush(loan);

        assertNotNull(loan.getLoanId());

        Loan savedLoan = loanRepository
                .findById(loan.getLoanId())
                .orElseThrow();

        assertEquals(checkoutDate, savedLoan.getCheckoutDate());
        assertEquals(dueDate, savedLoan.getDueDate());
        assertEquals(loanStats.READY, savedLoan.getLoanStatus());

        assertNotNull(savedLoan.getAssetId());
        assertEquals(
                asset.getAssetId(),
                savedLoan.getAssetId().getAssetId()
        );
    }
}