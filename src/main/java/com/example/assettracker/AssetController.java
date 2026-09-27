package com.example.assettracker;
import java.util.List;
import org.springframework.web.bind.annotation.*;

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
}