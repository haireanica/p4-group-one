package com.example.assettracker;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Asset {

    @Id
    @Column(name = "assetId")
    private Long assetId;

    @ManyToOne
    @JoinColumn(name = "inventoryId")
    private Inventory inventory;

    private String name;

    @Column(name = "assetStatus")
    private String assetStatus;

    public Asset() {}

    public Asset(Long assetId, Inventory inventory, String name, String assetStatus) {
        this.assetId = assetId;
        this.inventory = inventory;
        this.name = name;
        this.assetStatus = assetStatus;
    }

    public Long getAssetId() { return assetId; }
    public Inventory getInventory() { return inventory; }
    public String getName() { return name; }
    public String getAssetStatus() { return assetStatus; }
}
