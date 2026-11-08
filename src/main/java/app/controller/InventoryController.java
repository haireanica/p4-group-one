package app.controller;

import app.domain.Center;
import app.domain.Inventory;
import app.repository.CenterRepository;
import app.repository.InventoryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/inventories")
public class InventoryController {

    private final InventoryRepository inventoryRepository;
    private final CenterRepository centerRepository;

    public InventoryController(InventoryRepository inventoryRepository, CenterRepository centerRepository) {
        this.inventoryRepository = inventoryRepository;
        this.centerRepository = centerRepository;
    }

    @GetMapping
    public List<Inventory> getAllInventories() {
        return inventoryRepository.findAll();
    }

    /**
     * Finds an inventory associated with the given center.
     * Creates a new inventory record only if none exists for that center.
     */
    @PostMapping("/ensure")
    public ResponseEntity<Inventory> ensureInventoryForCenter(@RequestBody Center centerInput) {
        if (centerInput == null) {
            return ResponseEntity.badRequest().build();
        }

        Center center = null;
        if (centerInput.getCenterId() > 0) {
            center = centerRepository.findById(centerInput.getCenterId()).orElse(null);
        }

        if (center == null) {
            center = centerRepository.save(centerInput);
        }

        // Check if an inventory exists; create one on the fly if missing
        Optional<Inventory> existingInventory = inventoryRepository.findByCenter(center);
        if (existingInventory.isPresent()) {
            return ResponseEntity.ok(existingInventory.get());
        }

        Inventory newInventory = new Inventory(center);
        Inventory savedInventory = inventoryRepository.save(newInventory);
        return ResponseEntity.ok(savedInventory);
    }
}