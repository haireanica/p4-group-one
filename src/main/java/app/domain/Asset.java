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
 * The class Object of Asset which is the devices that will be rented out.
 */
@Entity
@Table(name = "asset")
public class Asset {
    /**
     * The attributes of the Asset class.
     * The assetId is the primary key for the Asset entity.
     */
    // These are the attributes that will be utilized to keep track and manage Assets
    //assetId is the primaryKey
    @Id
    @Column(name ="assetId")
    private String assetId;


    private String name;
    

    /**
     * The status of the asset will be used to determine if the asset is available for deployment or not.
     * The Enumerated annotation means there are only a limited number of values that status can be
     * The values are defined in the assetStatus enum class.
     * The .STRING means that values must be a String, and the length of the string is limited to 12 characters.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "assetStatus", length = 12)
    private assetStatus status;

    /**
     * The maintenanceRecords attribute is a list of Maintenance objects that are associated with the Asset.
     * The @OneToMany annotation means that one Asset can have many Maintenance records.
     */
    @OneToMany(mappedBy = "asset")
    private List<Maintenance> maintenanceRecords
            = new ArrayList<>();

    /**
     * The inventory attribute is a reference to the Inventory object that the Asset belongs to.
     * The @ManyToOne annotation means that many Assets can belong to one Inventory.
     */
    @ManyToOne
    @JoinColumn(name = "inventoryId")
    private Inventory inventory;

    /**
     * The constructors for the Asset class.
     * The default constructor is protected to prevent instantiation without parameters.
     */
    protected Asset() {

    }
    public Asset(String assetId, String name, assetStatus status,
                 List<Maintenance> maintenanceRecords) {
        this.assetId = assetId;
        this.name = name;
        this.status = status;
        this.maintenanceRecords = maintenanceRecords;
    }

    public void setStatus(assetStatus status) {
        this.status = status;
    }

    //Getters and setters

    public String getAssetId() {
        return assetId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public assetStatus getStatus() {
        return status;
    }

    public boolean isDeployable() {
        return status == assetStatus.AVAILABLE;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Overide allows for the attributes of the asset to be printed in a readable format when the object is printed.
     * returns all attributes of the asset in a string format. 
     */
    @Override
    public String toString() {
        return "Asset{" +
                "assetId='" + assetId + '\'' +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", deployable=" + isDeployable() +
                '}';
    }


}
