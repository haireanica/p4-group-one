package App.Repository;

import App.Domain.Asset;

import java.util.List;
import java.util.Optional;

//Method Overview:
// - save(Asset a): Handles saving or updating an asset and returns the save object.
// -findByTag(String assetTag): Looks up an asset by its tag and returns it wrapped in Optional
// so we avoid null pointer issues if it's not found.
// -findAll(): Grabs and returns All assets in Inventory.
// -delete(String assetId): Removes an asset by id and returns tue or false depending on success.


/**
 * Persistence boundary for assets
 */
public interface AssetRepository {

    AssetRepository.save(asset);

    AssetRepository.findById(assetId);

    AssetRepository.findAll();

    AssetRepository.deleteById(assetId);
