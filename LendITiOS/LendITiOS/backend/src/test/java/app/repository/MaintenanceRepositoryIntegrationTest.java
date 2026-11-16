package app.repository;

import app.domain.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import jakarta.persistence.EntityManager;

@SpringBootTest
@Transactional
public class MaintenanceRepositoryIntegrationTest {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private AssetRepository assetRepository;

    @Autowired
    private MaintenanceRepository maintenanceRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void shouldSaveAndRetrieveMaintenanceWithAsset() {
        int centerId = 998877;
        int inventoryId = 554433;

        // Bypass JPA detached entity merge issues by using native inserts for manual primary keys
        entityManager.createNativeQuery(
                        "INSERT INTO center (centerId, name, address_line1, city, state, postal_code) VALUES (?, ?, ?, ?, ?, ?)")
                .setParameter(1, centerId)
                .setParameter(2, "Maintenance Test Center")
                .setParameter(3, "456 Test Avenue")
                .setParameter(4, "Orlando")
                .setParameter(5, "FL")
                .setParameter(6, "32801")
                .executeUpdate();

        entityManager.createNativeQuery(
                        "INSERT INTO inventory (inventoryId, centerId) VALUES (?, ?)")
                .setParameter(1, inventoryId)
                .setParameter(2, centerId)
                .executeUpdate();

        entityManager.clear();

        Inventory inventory = inventoryRepository.findById(inventoryId).orElseThrow();

        Asset asset = new Asset(
                "TEST-MAINT-ASSET-001",
                "Maintenance Test Laptop",
                assetStatus.AVAILABLE,
                new ArrayList<Maintenance>(),
                "Laptop"
        );

        asset.setInventory(inventory);

        asset = assetRepository.saveAndFlush(asset);

        LocalDate openDate = LocalDate.now();

        Maintenance maintenance = new Maintenance(
                "Battery will not charge",
                maintStat.IN_PROGRESS,
                "Diagnostic testing started",
                openDate,
                null,
                asset
        );

        maintenance = maintenanceRepository.saveAndFlush(maintenance);

        assertNotNull(maintenance.getMaintenanceId());

        Maintenance savedMaintenance = maintenanceRepository
                .findById(maintenance.getMaintenanceId())
                .orElseThrow();

        assertEquals(
                maintenance.getMaintenanceId(),
                savedMaintenance.getMaintenanceId()
        );

        assertEquals(
                "Battery will not charge",
                savedMaintenance.getIssue()
        );

        assertEquals(
                "Diagnostic testing started",
                savedMaintenance.getRepairNotes()
        );

        assertEquals(
                maintStat.IN_PROGRESS,
                savedMaintenance.getMaintenaceStatus()
        );

        assertNull(
                savedMaintenance.getCompletedDate()
        );

        assertNotNull(savedMaintenance.getAsset());

        assertEquals(
                "TEST-MAINT-ASSET-001",
                savedMaintenance.getAsset().getAssetId()
        );
    }
}