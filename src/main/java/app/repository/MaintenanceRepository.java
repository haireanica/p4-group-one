package app.repository;

import app.domain.Maintenance;
import app.analytics.MaintenanceByCenterDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

/**
 * Provides database operations for {@link Maintenance} records.
 */
public interface MaintenanceRepository
        extends JpaRepository<Maintenance, Integer> {

    @Query("SELECT new app.analytics.MaintenanceByCenterDTO(c.name, COUNT(m)) " +
            "FROM Maintenance m " +
            "JOIN m.asset a " +
            "JOIN a.inventory i " +
            "JOIN i.center c " +
            "GROUP BY c.name")
    List<MaintenanceByCenterDTO> getMaintenanceByCenter();

    /**
     * Finds all maintenance records associated with an asset.
     *
     * @param assetId unique identifier of the asset
     * @return maintenance records associated with the specified asset
     */
    List<Maintenance> findByAsset_AssetId(String assetId);
}