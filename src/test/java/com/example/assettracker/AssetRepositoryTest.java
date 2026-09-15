package com.example.assettracker;

import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class AssetRepositoryTest {

    @Autowired
    private AssetRepository repo;

    @Test
    void assetCreation() {
        Asset testAsset = new Asset("TAG-1", "laptop", "AVAILABLE");
        Asset saved = repo.save(testAsset);

        Optional<Asset> result = repo.findById(saved.getId());

        assertThat(result).isPresent();
        assertThat(result.get().getAssetTag()).isEqualTo("TAG-1");

        System.out.println(saved.getId());
        System.out.println("Asset Tag: " + result.get().getAssetTag());

    }

}
