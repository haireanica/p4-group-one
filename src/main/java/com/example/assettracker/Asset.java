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
    private String assetId;

    @ManyToOne
    @JoinColumn(name = "inventoryId")
    private Inventory inventory;

    private String name;

    @Column(name = "device_type")
    private String deviceType;

    @Column(name = "assetStatus")
    private String assetStatus;

    public Asset() {}

    public Asset(String assetId, Inventory inventory, String name, String assetStatus, String deviceType) {
        this.assetId = assetId;
        this.inventory = inventory;
        this.deviceType = deviceType;
        this.name = name;
        this.assetStatus = assetStatus;
    }

    public String getAssetId() { return assetId; }
    public Inventory getInventory() { return inventory; }
    public String getDeviceType() { return deviceType; }
    public String getName() { return name; }
    public String getAssetStatus() { return assetStatus; }

    public void setAssetStatus(String assetStatus) { this.assetStatus = assetStatus; }
}
