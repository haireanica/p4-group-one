package app.repository;

import app.domain.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MaintenanceRepositoryIntegrationTest {

    @Autowired
    private CenterRepository centerRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private AssetRepository assetRepository;

    @Autowired
    private MaintenanceRepository maintenanceRepository;

    @Test
    void shouldSaveAndRetrieveMaintenanceWithAsset() {


        // 1. Create and save Center
        Center center = new Center(
                "Maintenance Test Center",
                "456 Test Avenue",
                null,
                "Orlando",
                "FL",
                "32801"
        );

        center = centerRepository.saveAndFlush(center);

        // 2. Create and save Inventory
        Inventory inventory = new Inventory(center);

        inventory = inventoryRepository.saveAndFlush(inventory);

        // 3. Create and Save Asset
        Asset asset = new Asset(
                "TEST-MAINT-ASSET-001",
                "Maintenance Test Laptop",
                assetStatus.AVAILABLE,
                new ArrayList<Maintenance>()
        );

        asset.setInventory(inventory);

        asset = assetRepository.saveAndFlush(asset);

        // 4. Create Maintenance record
        LocalDate openDate = LocalDate.now();

        Maintenance maintenance = new Maintenance(
                "Battery will not charge",
                maintStat.IN_PROGRESS,
                "Diagnostic testing started",
                openDate,
                null,
                asset

        );

        // 5. Save Maintenance to MySQL
        maintenance = maintenanceRepository.saveAndFlush(maintenance);

        // 6. Verify database generated the Maintenance ID
        assertNotNull(maintenance.getMaintenanceId());

        //7. Retrieve Maintenance from MySQL
        Maintenance savedMaintenance = maintenanceRepository
                .findById(maintenance.getMaintenanceId())
                .orElseThrow();

        // 8. Verify basic maintenance data
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

        // 9. Verify Asset relationship
        assertNotNull(savedMaintenance.getAsset());

        assertEquals(
                "TEST-MAINT-ASSET-001",
                savedMaintenance.getAsset().getAssetId()
        );




    }


}
