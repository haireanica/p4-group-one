package App.Domain;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;


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
    private Boolean deployable;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Maintenance> maintenanceRecords;

    //Constructors

    protected Asset() {

    }
    public Asset(String assetId, String name, assetStatus status, Boolean deployable, List<Maintenance> maintenanceRecords) {
        this.assetId = assetId;
        this.name = name;
        this.status = status;
        this.deployable = deployable;
        this.maintenanceRecords = maintenanceRecords;
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

    public Boolean getDeployable() {
        return deployable;
    }

    public void setDeployable(Boolean deployable) {
        this.deployable = deployable;
    }

    @Override
    public String toString() {
        return "Asset{" +
                "assetId='" + assetId + '\'' +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", deployable=" + deployable +
                '}';
    }


}
