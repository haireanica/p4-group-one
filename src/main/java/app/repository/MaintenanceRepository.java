package app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import app.domain.Maintenance;

/**
 * Provides database operations for {@link Maintenance} records.
 */
public interface MaintenanceRepository
        extends JpaRepository<Maintenance, Integer> {

    /**
     * Finds all maintenance records associated with an asset.
     *
     * @param assetId unique identifier of the asset
     * @return maintenance records associated with the specified asset
     */
    List<Maintenance> findByAsset_AssetId(String assetId);
}