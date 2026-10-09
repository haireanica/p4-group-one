package app.domain;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**  The maintenance class will be used to keep track of the maintenance records that are associated with an asset in OrlandoLendIT.
 * It will change the asset to an Asset object. This will allow for the maintenance record to be associated with a specific asset, 
 * and will allow for the maintenance record to be tracked and managed more easily.
 */
@Entity
@Table(name= "maintenance")
public class Maintenance {

    /**
     * Class attributes
     * The maintenanceId will be the class primary key, and will be used to identify the maintenance record in the database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maintenanceId;



    /**
     * The issue attribute will be used to describe the issue that is being reported for the asset.
     * The issues is limited to 40 characters, and will be stored in the database as a string.
     */
    @Column(length = 40)
    private String issue;




    /**
     * The maintenanceStatus attribute will be used to track the status of the maintenance record.
     * The Enumerated annotation means there are only a limited number of values that maintenanceStatus can be
     * The values are defined in the maintStat enum class.
     * The .STRING means that values must be a String, and the length of the string is limited to 15 characters.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "maintenanceStatus", length = 15)
    private maintStat MaintenanceStatus;

    @Column(length = 100)
    private String repairNotes;

    @Column(name = "openedDate")
    private LocalDate openDate;


    private LocalDate completedDate;

    /**
     * The asset attribute is a reference to the Asset object that the maintenance record is associated with.
     * The @ManyToOne annotation means that many maintenance records can be associated with one Asset
     */
    @ManyToOne
    @JoinColumn(name = "assetId")
    private Asset asset;

    /**
     * Constructors
     */
    protected Maintenance() {

    }

    public Maintenance(
            String issue,
            maintStat maintenanceStatus,
            String repairNotes,
            LocalDate openDate,
            LocalDate completedDate,
            Asset asset
    ) {
        this.issue = issue;
        this.MaintenanceStatus = maintenanceStatus;
        this.repairNotes = repairNotes;
        this.openDate = openDate;   
        this.completedDate = completedDate;
        this.asset = asset;
    }

    /**
     * Getters and Setters
     */

    public Integer getMaintenanceId() {
        return maintenanceId;
    }

    /**
     * Sets the status of the maintenance record.
     * @param status The new status of the maintenance record.
     */
     public void setMaintenanceStatus (maintStat status) {
            this.MaintenanceStatus = status;
     }

     public Asset getAssetId() {
        return asset;
     }

     public void setAssetId(Asset asset) {
        this.asset = asset;
     }

     public Asset getAsset() {
        return asset;
     }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }


    public String getRepairNotes() {
        return repairNotes;
    }

    public void setRepairNotes(String repairNotes) {
        this.repairNotes = repairNotes;
    }

    public LocalDate getOpenDate() {
        return openDate;
    }

    public void setOpenDate(LocalDate openDate) {
        this.openDate = openDate;
    }

    public LocalDate getCompletedDate() {
        return completedDate;
    }

    public void setCompletedDate(LocalDate completedDate) {
        this.completedDate = completedDate;
    }


    public maintStat getMaintenaceStatus() {
        return MaintenanceStatus;
    }

    /**
     * Override allows for the attributes of the maintenance record to be printed in a readable format when the object is printed.
     * returns all attributes of the maintenance record in a string format.
     */
    @Override
    public String toString() {
        return "Maintenance{" +
                "maintenanceId='" + maintenanceId + '\'' +
                ", issue='" + issue + '\'' +
                ", MaintenanceStatus=" + MaintenanceStatus +
                ", repairNotes='" + repairNotes + '\'' +
                ", openDate=" + openDate +
                ", completedDate=" + completedDate +
                '}';
    }
}
