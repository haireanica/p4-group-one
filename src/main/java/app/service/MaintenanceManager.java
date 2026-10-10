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
      * Creates a maintenance manager using the provided repositories.
      * 
      * @param maintenanceRepository the maintenance repository
      * @param assetRepository the asset repository
      */

    private final MaintenanceRepository maintenanceRepository;
    private final AssetRepository assetRepository;

    /**
     * Creates a maintenance manager using the provided repositories.
     * 
     * @param maintenanceRepository the maintenance repository
     * @param assetRepository the asset repository
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
     * 
     * @param maintenance the maintenance record to save
     * @return the saved maintenance record
     */
    public Maintenance save(Maintenance maintenance) {
        return maintenanceRepository.save(maintenance);
    }

    /**
     * The findById method is used to find a maintenance record by its ID.
     * It takes the maintenance ID as a parameter and returns the Maintenance object.
     * 
     * @param id the ID of the maintenance record to find
     * @return the found maintenance record
     * @throws IllegalArgumentException if no maintenance record exists with the given ID
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
     * 
     * @return a list of all maintenance records 
     * @throws IllegalArgumentException if no maintenance records exist
     * @throws IllegalStateException if there is an error retrieving the maintenance records
     * @throws RuntimeException if there is an unexpected error
     */
    public List<Maintenance> findAll() {
        return maintenanceRepository.findAll();
    }

    /**
     * The findByAssetId method is used to find all maintenance records for a specific asset.
     * It takes the asset ID as a parameter and returns a list of Maintenance objects.
     * 
     * @param assetId the ID of the asset for which to find maintenance records
     * @return a list of maintenance records for the specified asset
     * @throws IllegalArgumentException if no maintenance records exist for the specified asset
     */
    public List<Maintenance> findByAssetId(String assetId) {
        return maintenanceRepository.findByAsset_AssetId(assetId);
    }

     /**
      * The openMaintenance method is used to create a new maintenance record for an asset.
      * It takes the asset ID and issue description as parameters and returns the created Maintenance object.
      * 
      * @param assetId the ID of the asset for which to create a maintenance record
      * @param issue the issue description
      * @return the created maintenance record
      * @throws IllegalArgumentException if the asset is not found or if the issue description is invalid
      * @throws IllegalStateException if the asset is not available for maintenance
      * @throws RuntimeException if there is an unexpected error
      * 
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
     * 
     * @param maintenanceId the ID of the maintenance record to complete
     * @param repairNotes the notes about the repair
     * @return the completed maintenance record
     * @throws IllegalArgumentException if the maintenance record is not found or if the repair notes are invalid
     * @throws IllegalStateException if the maintenance record is not in progress
     * @throws RuntimeException if there is an unexpected error
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
