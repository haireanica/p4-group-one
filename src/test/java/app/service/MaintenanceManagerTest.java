package app.service;

import app.domain.*;
import app.repository.AssetRepository;
import app.repository.MaintenanceRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class MaintenanceManagerTest {

    private MaintenanceRepository maintenanceRepository;
    private AssetRepository assetRepository;
    private MaintenanceManager manager;


    @BeforeEach
    void setUp() {
        maintenanceRepository = mock(MaintenanceRepository.class);
        assetRepository = mock(AssetRepository.class);

        manager = new MaintenanceManager(
                maintenanceRepository,
                assetRepository
        );
    }

    @Test
    void openMaintenanceSuccess() {
        //Arrange: Create an available asset
        Asset asset = new Asset(
                "Lap100",
                "ThinkPad 3",
                assetStatus.AVAILABLE,
                List.of()

        );

        when(assetRepository.findById("Lap100"))
                .thenReturn(Optional.of(asset));

        when(maintenanceRepository.save(any(Maintenance.class)))
                .thenAnswer(invocation ->
                        invocation.getArgument(0)
                );

        // Act: Open Maintenance
        Maintenance result = manager.openMaintenance(
                "Lap100",
                "Screen is broken"
        );

        //Assert: Verify maintenance record
        assertNotNull(result);

        assertEquals(
                maintStat.IN_PROGRESS,
                result.getMaintenaceStatus()
        );

        assertEquals(
                "Screen is broken",
                result.getIssue()
        );

        // Verify asset is no longer available
        assertEquals(
                assetStatus.IN_MAINTENANCE,
                asset.getStatus()
        );

        // Verify repository operations
        verify(assetRepository).save(asset);

        verify(maintenanceRepository)
                .save(any(Maintenance.class));
    }

    @Test
    void openMaintenanceRejectsMissingAsset() {

        //Arrange: Asset does not exist
        when(assetRepository.findById("Lap999"))
                .thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(
                IllegalArgumentException.class,
                () -> manager.openMaintenance(
                        "Lap999",
                        "Broken screen"
                )
        );

        // Verify nothing was saved
        verify(assetRepository, never())
                .save(any(Asset.class));

        verify(maintenanceRepository, never())
                .save(any(Maintenance.class));
    }

    @Test
    void openMaintenanceRejectsUnavailableAsset() {

        // Arrange: Asset is currently checked out
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
                () -> manager.openMaintenance(
                        "Lap100",
                        "Keyboard is damaged"
                )
        );

        //Asset should remain checked out
        assertEquals(
                assetStatus.CHECKED_OUT,
                asset.getStatus()
        );

        // Verify nothing was saved
        verify(assetRepository, never())
                .save(any(Asset.class));

        verify(maintenanceRepository, never())
                .save(any(Maintenance.class));
    }

    @Test
    void openMaintenanceRejectsBlankIssue() {

        //Arrange: Asset exists and is available
        Asset asset = new Asset(
                "Lap100",
                "ThinkPad 3",
                assetStatus.AVAILABLE,
                List.of()
        );

        when(assetRepository.findById("Lap100"))
                .thenReturn(Optional.of(asset));

        // Act and Assert
        assertThrows(
                IllegalArgumentException.class,
                () -> manager.openMaintenance(
                        "Lap100",
                        "  "
                )
        );

        // Asset must remain available
        assertEquals(
                assetStatus.AVAILABLE,
                asset.getStatus()
        );

        // Verify nothing was saved
        verify(assetRepository, never())
                .save(any(Asset.class));

        verify(maintenanceRepository, never())
                .save(any(Maintenance.class));
    }
}
