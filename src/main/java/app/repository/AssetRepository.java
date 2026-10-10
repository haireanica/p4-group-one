package app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.domain.Asset;

/**
 * Provides database operations for {@link Asset} records.
 * 
 */
public interface AssetRepository extends JpaRepository<Asset, String> {

    


}

