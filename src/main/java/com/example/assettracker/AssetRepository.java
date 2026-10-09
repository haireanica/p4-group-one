package com.example.assettracker;

import com.example.assettracker.analytics.DeviceByTypeDTO;
import com.example.assettracker.analytics.DeviceStatusDistributionDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AssetRepository extends JpaRepository<Asset, Long> {
    long count();
    long countByAssetStatus(String assetStatus);

    @Query("SELECT new com.example.assettracker.analytics.DeviceByTypeDTO(a.name, COUNT(a)) FROM Asset a GROUP BY a.name")
    List<DeviceByTypeDTO> getAssetsByType();

    @Query("SELECT new com.example.assettracker.analytics.DeviceStatusDistributionDTO(a.assetStatus, COUNT(a)) FROM Asset a GROUP BY a.assetStatus")
    List<DeviceStatusDistributionDTO> getDeviceStatusDistribution();

}
