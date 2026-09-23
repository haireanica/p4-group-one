package app.service;

import app.domain.Asset;
import app.domain.Maintenance;
import app.domain.assetStatus;
import app.domain.maintStat;

import app.repository.AssetRepository;
import app.repository.MaintenanceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class MaintenanceManager {

    private final MaintenanceRepository maintenanceRepository;
    private final AssetRepository assetRepository;

    public MaintenanceManager(
            MaintenanceRepository maintenanceRepository,
            AssetRepository assetRepository
    ) {
        this.maintenanceRepository = maintenanceRepository;
        this.assetRepository = assetRepository;
    }

    public Maintenance save(Maintenance maintenance) {
        return maintenanceRepository.save(maintenance);
    }

    public Maintenance findById(Integer id) {
        return maintenanceRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Maintenance record not found: " + id
                        )
                );
    }

    public List<Maintenance> findAll() {
        return maintenanceRepository.findAll();
    }

    public List<Maintenance> findByAssetId(String assetId) {
        return maintenanceRepository.findByAsset_AssetId(assetId);
    }

    @Transactional
    public Maintenance openMaintenance(
            String assetId,
            String issue
    ) {
        // 1. Find the asset
        Asset asset = assetRepository.findById(assetId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Asset not found: " + assetId
                        )
                );
        //2. Verify the asset is available
        if (!asset.isDeployable()) {
            throw new IllegalStateException(
                    "Asset is not available for maintenance"
            );
        }

        //3 validate the reported issue
        if (issue == null || issue.isBlank()) {
            throw new IllegalArgumentException(
                    "Maintenance issue is required"
            );
        }

        // 4. Create maintenance record
        Maintenance maintenance = new Maintenance(
                issue,
                maintStat.IN_PROGRESS,
                "",
                LocalDate.now(),
                null,
                asset
        );

        // 5. Update asset status
        asset.setStatus(assetStatus.IN_MAINTENANCE);

        // 6. Save both changes
        assetRepository.save(asset);

        return maintenanceRepository.save(maintenance);
    }

    @Transactional
    public Maintenance completemaintenance(
            Integer maintenanceId,
            String repairNotes
    ) {
        // Find maintenance record
        Maintenance maintenance = maintenanceRepository
                .findById(maintenanceId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Maintenance record not found: " + maintenanceId
                        )
                );

        // Verify maintenance is actually in progress
        if (maintenance.getMaintenaceStatus() != maintStat.IN_PROGRESS) {
            throw new IllegalStateException(
                    "Maintenance is not in progress"
            );
        }

        // Validate repair notes
        if (repairNotes == null || repairNotes.isBlank()) {
            throw new IllegalArgumentException(
                    "Repair notes is required"
            );
        }

        Asset asset = maintenance.getAsset();

        // Complete maintenance record
        maintenance.setMaintenanceStatus(maintStat.COMPLETED);
        maintenance.setRepairNotes(repairNotes);
        maintenance.setCompletedDate(LocalDate.now());

        // Return asset to circulation
        asset.setStatus(assetStatus.AVAILABLE);

        assetRepository.save(asset);

        return maintenanceRepository.save(maintenance);
    }
}
