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
    private Integer inventoryId;

    @ManyToOne
    @JoinColumn(name = "centerId")
    private Center center;

    public Inventory() {}

    public Inventory(Integer inventoryId, Center center) {
        this.inventoryId = inventoryId;
        this.center = center;
    }

    public Integer getInventoryId() { return inventoryId; }
    public Center getCenter() { return center; }
}
