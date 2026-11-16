package app.repository;

import app.domain.Asset;
import app.domain.Center;
import app.domain.Inventory;
import app.domain.assetStatus;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class AssetRepositoryIntegrationTest {

    @Autowired
    private AssetRepository assetRepository;

    @Autowired
    private CenterRepository centerRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void repositoryLoads() {
        assertNotNull(assetRepository);
    }

    @Test
    void savesAndFindsAssetWithInventoryAndCenter() {
        int centerId = 998877;
        int inventoryId = 554433;

        entityManager.createNativeQuery(
                        "INSERT INTO center (centerId, name, address_line1, city, state, postal_code) VALUES (?, ?, ?, ?, ?, ?)")
                .setParameter(1, centerId)
                .setParameter(2, "Orlando Test Center")
                .setParameter(3, "123 Test Street")
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

        Center center = centerRepository.findById(centerId).orElseThrow();
        Inventory inventory = inventoryRepository.findById(inventoryId).orElseThrow();

        Asset asset = new Asset(
                "TEST-LAPTOP-001",
                "Test Laptop",
                assetStatus.AVAILABLE,
                new ArrayList<>(),
                "Laptop"
        );

        asset.setInventory(inventory);
        assetRepository.saveAndFlush(asset);

        entityManager.clear();

        Asset found = assetRepository.findById("TEST-LAPTOP-001")
                .orElseThrow();

        assertEquals("TEST-LAPTOP-001", found.getAssetId());
        assertEquals("Test Laptop", found.getName());
        assertEquals(assetStatus.AVAILABLE, found.getStatus());

        assertNotNull(found.getInventory());
        assertEquals(inventoryId, found.getInventory().getInventoryId());

        assertNotNull(found.getInventory().getCenter());
        assertEquals("Orlando Test Center", found.getInventory().getCenter().getName());
    }
}