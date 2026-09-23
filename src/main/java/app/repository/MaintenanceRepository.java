package app.repository;

import app.domain.Maintenance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface MaintenanceRepository
        extends JpaRepository<Maintenance, Integer> {

    List<Maintenance> findByAsset_AssetId(String assetId);
}
