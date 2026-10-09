package com.example.assettracker;

import com.example.assettracker.analytics.MaintenanceByCenterDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MaintenanceRepository extends JpaRepository<Maintenance, Long> {
    @Query("SELECT new com.example.assettracker.analytics.MaintenanceByCenterDTO(c.name, COUNT(m)) " +
            "FROM Maintenance m " +
            "JOIN m.asset a " +
            "JOIN a.inventory i " +
            "JOIN i.center c " +
            "GROUP BY c.name")
    List<MaintenanceByCenterDTO> getMaintenanceByCenter();
}
