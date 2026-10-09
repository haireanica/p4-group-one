package app.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.domain.Asset;
import app.domain.Maintenance;
import app.domain.assetStatus;
import app.domain.maintStat;
import app.repository.AssetRepository;
import app.repository.MaintenanceRepository;



/**
 * Service annotation indicates that this class is a service component in the Spring framework.
 * It is responsible for handling business logic related to maintenance records 
 * and interacting with the repositories to perform CRUD operations on the Maintenance entity.
 */
@Service
public class MaintenanceManager {

     /**
      * Class attributes
      * The maintenanceRepository attribute is a reference to the MaintenanceRepository that will be used to persist
      * and retrieve maintenance records from the database. It will be used to perform CRUD operations on the Maintenance entity.
      */

    private final MaintenanceRepository maintenanceRepository;
    private final AssetRepository assetRepository;

    /**
     * Constructor
     */
    public MaintenanceManager(
            MaintenanceRepository maintenanceRepository,
            AssetRepository assetRepository
    ) {
        this.maintenanceRepository = maintenanceRepository;
        this.assetRepository = assetRepository;
    }

    /**
     * The save method is used to save a maintenance record to the database.
     * It takes a Maintenance object as a parameter and returns the saved Maintenance object.
     */
    public Maintenance save(Maintenance maintenance) {
        return maintenanceRepository.save(maintenance);
    }

    /**
     * The findById method is used to find a maintenance record by its ID.
     * It takes the maintenance ID as a parameter and returns the Maintenance object.
     */
    public Maintenance findById(Integer id) {
        return maintenanceRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Maintenance record not found: " + id
                        )
                );
    }

    /**
     * The findAll method is used to find all maintenance records.
     * It returns a list of all Maintenance objects.
     */
    public List<Maintenance> findAll() {
        return maintenanceRepository.findAll();
    }

    /**
     * The findByAssetId method is used to find all maintenance records for a specific asset.
     * It takes the asset ID as a parameter and returns a list of Maintenance objects.
     */
    public List<Maintenance> findByAssetId(String assetId) {
        return maintenanceRepository.findByAsset_AssetId(assetId);
    }

     /**
      * The openMaintenance method is used to create a new maintenance record for an asset.
      * It takes the asset ID and issue description as parameters and returns the created Maintenance object.
      */
    @Transactional
    public Maintenance openMaintenance(
            String assetId,
            String issue
    ) {
        /**
         * 1. Find the asset
         */
        Asset asset = assetRepository.findById(assetId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Asset not found: " + assetId
                        )
                );
        /**
         * 2. Verify the asset is available
         */
        if (!asset.isDeployable()) {
            throw new IllegalStateException(
                    "Asset is not available for maintenance"
            );
        }

        /**
         * 3 validate the reported issue
         */
        if (issue == null || issue.isBlank()) {
            throw new IllegalArgumentException(
                    "Maintenance issue is required"
            );
        }

        /**
         * 4. Create maintenance record
         */
        Maintenance maintenance = new Maintenance(
                issue,
                maintStat.IN_PROGRESS,
                "",
                LocalDate.now(),
                null,
                asset
        );

        /**
         * 5. Update asset status
         */
        asset.setStatus(assetStatus.IN_MAINTENANCE);

        /**
         * 6. Save both changes
         */
        assetRepository.save(asset);

        return maintenanceRepository.save(maintenance);
    }

    /**
     * Completes a maintenance record
     */
    @Transactional
    public Maintenance completemaintenance(
            Integer maintenanceId,
            String repairNotes
    ) {
        /**
         * Find maintenance record
         */
        Maintenance maintenance = maintenanceRepository
                .findById(maintenanceId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Maintenance record not found: " + maintenanceId
                        )
                );

        /**
         * Verify maintenance is actually in progress
         */
        if (maintenance.getMaintenaceStatus() != maintStat.IN_PROGRESS) {
            throw new IllegalStateException(
                    "Maintenance is not in progress"
            );
        }

        /**
         * Validate repair notes
         */
        if (repairNotes == null || repairNotes.isBlank()) {
            throw new IllegalArgumentException(
                    "Repair notes is required"
            );
        }

        Asset asset = maintenance.getAsset();

        /**
         * Complete maintenance record
         */
        maintenance.setMaintenanceStatus(maintStat.COMPLETED);
        maintenance.setRepairNotes(repairNotes);
        maintenance.setCompletedDate(LocalDate.now());

        /**
         * Return asset to circulation
         */
        asset.setStatus(assetStatus.AVAILABLE);

        assetRepository.save(asset);

        return maintenanceRepository.save(maintenance);
    }
}
