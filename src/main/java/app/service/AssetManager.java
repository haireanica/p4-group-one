package app.service;

import app.domain.Asset;
import app.domain.assetStatus;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Locale;

import app.repository.AssetRepository;

@Service
public class AssetManager {

    private final AssetRepository repo;

    public AssetManager(AssetRepository repo) {
        this.repo = repo;
    }

    public Asset save(Asset asset) {
        return repo.save(asset);
    }

    public Asset findById(String id) {
        return repo.findById(id).orElseThrow(() ->
                new IllegalArgumentException(
                        "Asset not found: " + id
                )
        );
    }

    public List<Asset> findAll() {
        return repo.findAll();
    }





    public static assetStatus parseStatus(String status) {
        if (status == null) throw new IllegalArgumentException("Status cannot be null.");
        String key = status.trim().toUpperCase(Locale.ROOT).replace(' ','_');
        return assetStatus.valueOf(key);
    }
    
    public int importAssets(List<Asset> parsed) {
        int added =  0;
        if (parsed == null) return 0;
        for (Asset asset : parsed) {
            if (asset == null) continue;
            if (add(asset)) added++;
        }
        return added;
    }

    private boolean add(Asset asset) {
        if (asset.getAssetId() == null || asset.getAssetId().isEmpty()){ return false;}

        if(repo.existsById(asset.getAssetId())) { return false; }
        repo.save(asset);
        return true;
    }

    public void retiredById(String id) {
        Asset asset = findById(id);
        asset.setStatus(assetStatus.RETIRED);
        repo.save(asset);
    }
}
