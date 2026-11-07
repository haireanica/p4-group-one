package app.controller;

import java.util.List;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import app.domain.Asset;
import app.service.AssetManager;

@RestController
@RequestMapping("/api/assets")
public class AssetController {

    private final AssetManager assetManager;

    public AssetController(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    @GetMapping
    public List<Asset> getAll() {
        return assetManager.findAll();
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Asset asset) {
        if (asset.getAssetId() == null || asset.getAssetId().isBlank()) {
            return ResponseEntity.badRequest().body("Asset ID is required.");
        }
        if (asset.getName() == null || asset.getName().isBlank()) {
            return ResponseEntity.badRequest().body("Asset name is required.");
        }

        try {
            if (assetManager.isDeployable(asset.getAssetId()) || assetManager.findAll().stream().anyMatch(a -> a.getAssetId().equals(asset.getAssetId()))) {
                return ResponseEntity.status(409).body("Asset with this number already exists.");
            }

            Asset savedAsset = assetManager.save(asset);
            return ResponseEntity.ok(savedAsset);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(409).body(e.getMessage());
        }
    }
}