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
    public Asset create(@RequestBody Asset asset) {
        // assetId is caller-assigned, not auto-generated (matches the
        // Database team's schema), the request body must include it.
        return repo.save(asset);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Asset> getById(@PathVariable("id") Long id){
        return repo.findById(id)
            .map(asset -> ResponseEntity.ok(asset))
            .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
