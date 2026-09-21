package app.repository;

import app.domain.Asset;

import org.springframework.data.jpa.repository.JpaRepository;


public interface AssetRepository extends JpaRepository<Asset, String> {


}

