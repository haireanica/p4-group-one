package app.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * Represents a device managed by the OrlandoLendIT lending system.
 *
 * <p>An asset belongs to an inventory and may have multiple maintenance
 * records. Its status determines whether it can be deployed to a resident.</p>
 */
@Entity
@Table(name = "asset")
public class Asset {

    @Id
    @Column(name = "assetId")
    private String assetId;

    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "assetStatus", length = 20)
    private assetStatus status;

    @Enumerated (EnumType.STRING)
    @Column(name = "assetType", length = 20)
    private assetType type;

    @OneToMany(mappedBy = "asset")
    private List<Maintenance> maintenanceRecords = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "inventoryId")
    private Inventory inventory;


    /**
     * Creates an empty asset for JPA.
     */
    protected Asset() {
    }

    /**
     * Creates an asset with its identifying information and maintenance history.
     *
     * @param assetId unique identifier assigned to the asset
     * @param name descriptive name of the asset
     * @param status current operational status
     * @param maintenanceRecords maintenance records associated with the asset
     */
    public Asset(
            String assetId,
            String name,
            assetStatus status,
            assetType type,
            List<Maintenance> maintenanceRecords) {

        this.assetId = assetId;
        this.name = name;
        this.status = status;
        this.type = type;
        this.maintenanceRecords = maintenanceRecords;
    }

    /**
     * Returns the asset's unique identifier.
     *
     * @return the asset identifier
     */
    public String getAssetId() {
        return assetId;
    }

    /**
     * Returns the asset's type.
     *
     * @return the asset type
     */
    public assetType getType() {
        return type;
    }

    /**
     * Returns the asset's descriptive name.
     *
     * @return the asset name
     */
    public String getName() {
        return name;
    }

    /**
     * Updates the asset's descriptive name.
     *
     * @param name new asset name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the asset's current operational status.
     *
     * @return the current asset status
     */
    public assetStatus getStatus() {
        return status;
    }

    /**
     * Updates the asset's operational status.
     *
     * @param status new operational status
     */
    public void setStatus(assetStatus status) {
        this.status = status;
    }

    /**
     * Determines whether the asset is available for deployment.
     *
     * @return {@code true} when the status is {@code AVAILABLE};
     *         otherwise {@code false}
     */
    public boolean isDeployable() {
        return status == assetStatus.AVAILABLE;
    }

    /**
     * Assigns the asset to an inventory.
     *
     * @param inventory inventory that contains the asset
     */
    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    /**
     * Returns the inventory containing this asset.
     *
     * @return the associated inventory
     */
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Returns a readable representation of the asset.
     *
     * @return asset information formatted as a string
     */
    @Override
    public String toString() {
        return "Asset{" +
                "assetId='" + assetId + '\'' +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", type=" + type +
                ", deployable=" + isDeployable() +
                '}';
    }
}