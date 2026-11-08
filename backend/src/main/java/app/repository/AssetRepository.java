package app.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import app.analytics.DeviceStatusDistributionDTO;
import app.domain.Asset;

/**
 * Provides database operations for {@link Asset} records.
 */
public interface AssetRepository extends JpaRepository<Asset, String> {

    long count();

    long countByStatus(app.domain.assetStatus status);

    @Query("SELECT new app.analytics.DeviceStatusDistributionDTO(CAST(a.status AS string), COUNT(a)) " +
            "FROM Asset a GROUP BY a.status")
    List<DeviceStatusDistributionDTO> getDeviceStatusDistribution();
}