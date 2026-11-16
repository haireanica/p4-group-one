package app.assettracker;

import app.domain.Asset;
import app.domain.assetStatus;
import java.util.ArrayList;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

import app.repository.AssetRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class AssetRepositoryTest {

    @Autowired
    private AssetRepository repo;

    @Test
    void assetCreation() {
        Asset testAsset = new Asset(
                "1",
                "TAG-1",
                assetStatus.AVAILABLE,
                new ArrayList<>(),
                "Laptop"
        );
        Asset saved = repo.save(testAsset);

        Optional<Asset> result = repo.findById(saved.getAssetId());
        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("TAG-1");
        System.out.println("Saved ID: " + saved.getAssetId());
    }
}