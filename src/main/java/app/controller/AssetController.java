package app.controller;

import app.domain.Asset;
import app.service.AssetManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping
    public ResponseEntity<?> createAsset(@RequestBody Asset asset) {
        boolean added = assetManager.add(asset);
        if (!added) {
            return ResponseEntity.status(409).body("Asset already exists or invalid ID.");
        }
        return ResponseEntity.ok(asset);
    }

    /**
     * Updates an existing asset when submitted from the Edit modal.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Asset> updateAsset(@PathVariable String id, @RequestBody Asset updatedAsset) {
        try {
            Asset existing = assetManager.findById(id);

            // Update fields
            existing.setName(updatedAsset.getName());
            existing.setDeviceType(updatedAsset.getDeviceType());
            existing.setStatus(updatedAsset.getStatus());

            if (updatedAsset.getInventory() != null) {
                existing.setInventory(updatedAsset.getInventory());
            }

            Asset saved = assetManager.save(existing);
            return ResponseEntity.ok(saved);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}