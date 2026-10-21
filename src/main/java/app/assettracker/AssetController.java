package app.assettracker;
import java.util.List;

import app.domain.Asset;
import app.service.AssetManager;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/assets")
public class AssetController {

    private final AssetManager assetManager;

    public AssetController(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    @GetMapping
    public List<Asset> getAllAssets() {
        return assetManager.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Asset> getAssetById(@PathVariable String id) {
        Asset asset = assetManager.findById(id);
        return ResponseEntity.ok(asset);
    }

    @PostMapping
    public ResponseEntity<?> createAsset(@RequestBody Asset asset) {
        try {
            Asset savedAsset = assetManager.save(asset);
            return ResponseEntity.status(201).body(savedAsset);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}/retire")
    public ResponseEntity<Void> retireAsset(@PathVariable String id) {
        assetManager.retiredById(id);
        return ResponseEntity.noContent().build();
    }
}
