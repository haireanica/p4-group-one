package app.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


/**
 * The class Object of Asset which is the devices that will be rented out.
 */
@Entity
@Table(name = "assets")
public class Asset {

    // These are the attributes that will be utilized to keep track and manage Assets
    //assetId is the primaryKey
    @Id
    private String assetId;
    private String name;
    @Enumerated(EnumType.STRING)
    private assetStatus status;
    @OneToMany(mappedBy = "asset")
    private List<Maintenance> maintenanceRecords
            = new ArrayList<>();

    //Constructors

    protected Asset() {

    }
    public Asset(String assetId, String name, assetStatus status, List<Maintenance> maintenanceRecords) {
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
