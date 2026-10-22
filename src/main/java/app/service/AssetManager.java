package app.service;

import java.util.List;
import java.util.Locale;

import org.springframework.stereotype.Service;

import app.domain.Asset;
import app.domain.assetStatus;
import app.repository.AssetRepository;

/**
 * Provides business operations for creating, finding, importing,
 * and retiring OrlandoLendIT assets.
 */
@Service
public class AssetManager {

    private final AssetRepository repo;

    /**
     * Creates an asset manager using the provided repository.
     *
     * @param repo repository used for asset persistence
     */
    public AssetManager(AssetRepository repo) {
        this.repo = repo;
    }

    /**
     * Saves an asset to the database.
     *
     * @param asset asset to save
     * @return the persisted asset
     */
    public Asset save(Asset asset) {
        return repo.save(asset);
    }

    /**
     * Finds an asset using its unique identifier.
     *
     * @param id unique identifier of the asset
     * @return the matching asset
     * @throws IllegalArgumentException if no asset exists with the given ID
     */
    public Asset findById(String id) {
        return repo.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Asset not found: " + id));
    }

    /**
     * Returns every asset stored in the database.
     *
     * @return all stored assets
     */
    public List<Asset> findAll() {
        return repo.findAll();
    }

    /**
     * Converts a text value into an asset status.
     *
     * @param status text representation of the status
     * @return the corresponding asset status
     * @throws IllegalArgumentException if {@code status} is null
     *         or does not represent a valid status
     */
    public static assetStatus parseStatus(String status) {
        if (status == null) {
            throw new IllegalArgumentException("Status cannot be null.");
        }

        String key = status.trim()
                .toUpperCase(Locale.ROOT)
                .replace(' ', '_');

        return assetStatus.valueOf(key);
    }

    /**
     * Imports assets that have valid, unused identifiers.
     *
     * @param parsed assets selected for import
     * @return number of assets successfully imported
     */
    public int importAssets(List<Asset> parsed) {
        int added = 0;

        if (parsed == null) {
            return 0;
        }

        for (Asset asset : parsed) {
            if (asset == null) {
                continue;
            }

            if (add(asset)) {
                added++;
            }
        }

        return added;
    }

    /**
     * Adds an asset to the database if it has a valid, unused identifier.
     * 
     * @param asset asset to add
     * @return true if the asset was added, false otherwise
     *  
     */
        private boolean add(Asset asset) {
        if (asset.getAssetId() == null || asset.getAssetId().isEmpty()){ return false;}

        if(repo.existsById(asset.getAssetId())) { return false; }
        repo.save(asset);
        return true;
    }

    /**
     * Retires an asset instead of permanently deleting its record.
     *
     * @param id unique identifier of the asset to retire
     * @throws IllegalArgumentException if no asset exists with the given ID
     */
    public void retiredById(String id) {
        Asset asset = findById(id);
        asset.setStatus(assetStatus.RETIRED);
        repo.save(asset);
    }

    /**
     * Determines whether an asset is available for checkout.
     * 
     * @param assetId unique identifier of the asset to check
     * @return true if the asset is available, false otherwise
     * @throws IllegalArgumentException if no asset exists with the given ID
     */
    public boolean isDeployable(String assetId) {
    return repo.findById(assetId)
            .map(asset -> asset.getStatus() == assetStatus.AVAILABLE)
            .orElse(false);
}
}