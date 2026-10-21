package app.repository;

import app.domain.Asset;
import app.analytics.DeviceByTypeDTO;
import app.analytics.DeviceStatusDistributionDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

/**
 * Provides database operations for {@link Asset} records.
 *
 */
public interface AssetRepository extends JpaRepository<Asset, String> {
    long count();
    long countByStatus(app.domain.assetStatus status);

    @Query("SELECT new app.analytics.DeviceStatusDistributionDTO(a.status, COUNT(a)) FROM Asset a GROUP BY a.status")
    List<DeviceStatusDistributionDTO> getDeviceStatusDistribution();

    @Query("SELECT new app.analytics.DeviceByTypeDTO(a.name, COUNT(a)) FROM Asset a GROUP BY a.name")
    List<DeviceByTypeDTO> getAssetsByType();
}

