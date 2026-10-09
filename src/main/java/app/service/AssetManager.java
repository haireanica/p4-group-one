package app.service;

import java.util.List;
import java.util.Locale;

import org.springframework.stereotype.Service;

import app.domain.Asset;
import app.domain.assetStatus;
import app.repository.AssetRepository;



@Service
public class AssetManager {

    /**
     * Class attributes
     * The repo attribute is a reference to the AssetRepository that will be used to persist and
     * retrieve assets from the database. It will be used to perform CRUD operations on the Asset entity.
     */
    private final AssetRepository repo;

    public AssetManager(AssetRepository repo) {
        this.repo = repo;
    }

    /**
     * The save method is used to save an asset to the database. 
     * It takes an Asset object as a parameter and returns the saved Asset object.
     * It uses the save method of the AssetRepository to persist the asset to the database.
     */
    public Asset save(Asset asset) {
        return repo.save(asset);
    }

    /**
     * The findById method is used to find an asset by its ID.
     * It takes the asset ID as a parameter and returns the Asset object.
     * It uses the findById method of the AssetRepository to retrieve the asset from the database.
     * If the asset is not found, it throws an IllegalArgumentException with a message indicating
     *  that the asset was not found.
     */
    public Asset findById(String id) {
        return repo.findById(id).orElseThrow(() ->
                new IllegalArgumentException(
                        "Asset not found: " + id
                )
        );
    }

    /**
     * The findAll method is used to find all assets in the database.
     * It returns a list of Asset objects.
     * It uses the findAll method of the AssetRepository to retrieve all assets from the database.
     */
    public List<Asset> findAll() {
        return repo.findAll();
    }




    /**
     * The parseStatus method is used to parse a string representation of an asset status into an assetStatus enum value.
     * It takes a string representation of the status as a parameter and returns the corresponding assetStatus
     * enum value. If the status is null, it throws an IllegalArgumentException with a message indicating that the status cannot be null.
     */
    public static assetStatus parseStatus(String status) {
        if (status == null) throw new IllegalArgumentException("Status cannot be null.");
        String key = status.trim().toUpperCase(Locale.ROOT).replace(' ','_');
        return assetStatus.valueOf(key);
    }
    
    /**
     * The importAssets method is used to import a list of assets into the database.
     * It takes a list of Asset objects as a parameter and returns the number of assets that
     * were successfully added to the database. It uses the add method to add each asset to the database.
     */
    public int importAssets(List<Asset> parsed) {
        int added =  0;
        if (parsed == null) return 0;
        for (Asset asset : parsed) {
            if (asset == null) continue;
            if (add(asset)) added++;
        }
        return added;
    }

    /**
     * The add method is used to add an asset to the database.
     * It takes an Asset object as a parameter and returns a boolean indicating whether the asset was
     * successfully added to the database. It checks if the asset ID is null or empty, and if the asset already exists in the database.
     * If the asset ID is null or empty, or if the asset already exists in the
     * database, it returns false. Otherwise, it saves the asset to the database and returns true.
     */
    private boolean add(Asset asset) {
        if (asset.getAssetId() == null || asset.getAssetId().isEmpty()){ return false;}

        if(repo.existsById(asset.getAssetId())) { return false; }
        repo.save(asset);
        return true;
    }

    /**
     * The retiredById method is used to retire an asset by its ID.
     * It takes a string representing the asset ID as a parameter and throws an IllegalArgumentException if the asset is not found.
     * It uses the findById method to retrieve the asset from the database, sets its status to RETIRED, and saves the updated asset back to the database.
     */
    public void retiredById(String id) {
        Asset asset = repo.findById(id).orElseThrow(
                () -> new IllegalArgumentException(
                        "Asset not found: " + id
                )
        );

        asset.setStatus(assetStatus.RETIRED);
        repo.save(asset);
    }
}
