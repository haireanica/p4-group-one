package app.repository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import app.domain.Asset;
import app.domain.Center;
import app.domain.Inventory;
import app.domain.assetStatus;
import app.domain.assetType;

@SpringBootTest
@Transactional
public class AssetRepositoryIntegrationTest {

    @Autowired
    private AssetRepository assetRepository;

    @Autowired
    private CenterRepository centerRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Test
    void repositoryLoads() {
        assertNotNull(assetRepository);
    }

    @Test
    void savesAndFindsAssetWithInventoryAndCenter() {

        // 1. Create and save Center
        Center center = new Center(

                "Orlando Test Center",
                "123 Test Street",
                null,
                "Orlando",
                "FL",
                "32801"
        );

        center = centerRepository.save(center);

        // 2. Create and save Inventory belonging to Center
        Inventory inventory = new Inventory(center);
        inventory = inventoryRepository.save(inventory);

        // 3. Create Asset and associate it with Inventory
        Asset asset = new Asset(
                "Test-LAPTOP-001",
                "Test Laptop",
                assetStatus.AVAILABLE,
                assetType.LAPTOP,
                new ArrayList<>()
        );

        asset.setInventory(inventory);

        assetRepository.saveAndFlush(asset);

        // 4. Read it back from MySQL
        Asset found = assetRepository.findById("TEST-LAPTOP-001")
                .orElseThrow();

        // 5. Verify Asset
        assertEquals("TEST-LAPTOP-001", found.getAssetId());
        assertEquals("Test Laptop", found.getName());
        assertEquals(assetStatus.AVAILABLE, found.getStatus());

        // 6. Verify relationships
        assertNotNull(found.getInventory());
        assertEquals(inventory.getInventoryId(),
                found.getInventory().getInventoryId());

        assertNotNull(found.getInventory().getCenter());
        assertEquals("Orlando Test Center",
                found.getInventory().getCenter().getName());
    }

    @Test
void confirmAssetTypeIsPersisted() {
    // Arrange
    Asset asset = new Asset(
            "TEST-TYPE-001",
            "Test Laptop",
            assetStatus.AVAILABLE,
            assetType.LAPTOP,
            List.of()
    );

    // Act
    assetRepository.saveAndFlush(asset);

    Asset savedAsset = assetRepository
            .findById("TEST-TYPE-001")
            .orElseThrow();

    // Assert
    assertEquals(
            assetType.LAPTOP,
            savedAsset.getType()
    );

    // Cleanup
    assetRepository.deleteById("TEST-TYPE-001");
}
}
