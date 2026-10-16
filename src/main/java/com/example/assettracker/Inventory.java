package com.example.assettracker;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Inventory {

    @Id
    @Column(name = "inventoryId")
    private Long inventoryId;

    @ManyToOne
    @JoinColumn(name = "centerId")
    private Center center;

    public Inventory() {}

    public Inventory(Long inventoryId, Center center) {
        this.inventoryId = inventoryId;
        this.center = center;
    }

    public Long getInventoryId() { return inventoryId; }
    public Center getCenter() { return center; }
}
