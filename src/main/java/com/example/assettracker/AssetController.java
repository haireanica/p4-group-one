package com.example.assettracker;
import java.util.List;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/assets")
public class AssetController {

    private final AssetRepository repo;

    public AssetController(AssetRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Asset> getAll() {
        return repo.findAll();          // reads from H2
    }

     

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Asset asset) {
         // Check for a missing asset ID
        if (asset.getAssetId() == null || asset.getAssetId().isBlank()) {
            return ResponseEntity.badRequest()
                    .body("Asset ID is required.");
        }

        // Check for a missing or blank name
        if (asset.getName() == null || asset.getName().isBlank()) {
            return ResponseEntity.badRequest()
                    .body("Asset name is required.");
        }

        // Checks if the asset ID already exists
        if(repo.existsById(asset.getAssetId())) {
            return ResponseEntity.status(409)
                    .body("Asset with this number already exists.");
        }
        return ResponseEntity.ok(repo.save(asset));
    }
}
