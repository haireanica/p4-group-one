package app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import app.domain.Maintenance;


public interface MaintenanceRepository
        extends JpaRepository<Maintenance, Integer> {

            /**
             * The MaintenanceRepo is to help the data be stored not just in memory but also in the database.
             * It also allows for the data to be retrieved from the database.
             *
             * The findByAsset_AssetId method is used to find all maintenance records for a specific asset.
             * It takes the asset ID as a parameter and returns a list of maintenance records.
             * This method is used to retrieve all maintenance records associated with a specific asset.
             */
    List<Maintenance> findByAsset_AssetId(String assetId);
}
