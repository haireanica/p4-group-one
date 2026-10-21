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
 * It will change the asset to an Asset object, and will change the maintenanceStatus to a maintStat enum class.
 * This will allow for the maintenance record to be associated with a specific asset and maintenance status,
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
    private maintStat maintenanceStatus;

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
     * The constructors for the Maintenance class.
     * The default constructor is protected to prevent instantiation without parameters.
     * The parameterized constructor allows for the creation of a Maintenance object with specific attributes.
     */
    protected Maintenance() {

    }

        /**
     * Creates a new Maintenance object with the specified attributes.
     *
     * @param issue the issue with the asset
     * @param maintenanceStatus the status of the maintenance
     * @param repairNotes notes about the repair
     * @param openDate the date the maintenance was opened
     * @param completedDate the date the maintenance was completed
     * @param asset the asset associated with the maintenance
     */
    public Maintenance(
            String issue,
            maintStat maintenanceStatus,
            String repairNotes,
            LocalDate openDate,
            LocalDate completedDate,
            Asset asset
    ) {
        this.issue = issue;
        this.maintenanceStatus = maintenanceStatus;
        this.repairNotes = repairNotes;
        this.openDate = openDate;   
        this.completedDate = completedDate;
        this.asset = asset;
    }


    /**
     * set the maintenance status of the maintenance record.
     * @param status the maintenance status to set
     * 
     */
     public void setMaintenanceStatus (maintStat status) {
            this.maintenanceStatus = status;
     }


     /**
      * Returns the asset associated with the maintenance record.
      * @return the asset associated with the maintenance record
      */
     public Asset getAssetId() {
        return asset;
     }

     /**
      * Sets the asset associated with the maintenance record.
      * @param asset the asset to associate with the maintenance record
      * 
      */
     public void setAssetId(Asset asset) {
        this.asset = asset;
     }

     /**
      * Returns the maintenance ID of the maintenance record.
      * @return the maintenance ID of the maintenance record
      */
     public Asset getAsset() {
        return asset;
     }

     /**
      * Returns the maintenance ID of the maintenance record.
      * @return the maintenance ID of the maintenance record
      */
    public String getIssue() {
        return issue;
    }

    /**
     * Sets the issue of the maintenance record.
     * @param issue the issue to set for the maintenance record
     * 
     */
    public void setIssue(String issue) {
        this.issue = issue;
    }

    /**
     * Returns the repair notes of the maintenance record.
     * @return the repair notes of the maintenance record
     */
    public String getRepairNotes() {
        return repairNotes;
    }

    /**
     * Sets the repair notes of the maintenance record.
     * @param repairNotes the repair notes to set for the maintenance record
     * 
     */
    public void setRepairNotes(String repairNotes) {
        this.repairNotes = repairNotes;
    }

    /**
     * Returns the open date of the maintenance record.
     * @return the open date of the maintenance record
     */
    public LocalDate getOpenDate() {
        return openDate;
    }

    /**
     * Sets the open date of the maintenance record.
     * @param openDate the open date to set for the maintenance record
     * 
     */
    public void setOpenDate(LocalDate openDate) {
        this.openDate = openDate;
    }

    /**
     * Returns the completed date of the maintenance record.
     * @return the completed date of the maintenance record
     */
    public LocalDate getCompletedDate() {
        return completedDate;
    }

    /**
     * Sets the completed date of the maintenance record.
     * @param completedDate the completed date to set for the maintenance record
     * 
     */
    public void setCompletedDate(LocalDate completedDate) {
        this.completedDate = completedDate;
    }


    /**
     * Returns the maintenance status of the maintenance record.
     * @return the maintenance status of the maintenance record
     */
    public maintStat getMaintenaceStatus() {
        return maintenanceStatus;
    }

    /**    (non-Javadoc)
     * gets the id of the maintenance record.
     * @return the maintenanceId of the maintenance record
     * 
     */
    public Integer getMaintenanceId() {
        return maintenanceId;
    }
    /**
     * Override allows for the attributes of the maintenance record to be printed in a readable format when the object is printed.
     * @return returns all attributes of the maintenance record in a string format.
     */
    @Override
    public String toString() {
        return "Maintenance{" +
                "maintenanceId='" + maintenanceId + '\'' +
                ", issue='" + issue + '\'' +
                ", MaintenanceStatus=" + maintenanceStatus +
                ", repairNotes='" + repairNotes + '\'' +
                ", openDate=" + openDate +
                ", completedDate=" + completedDate +
                '}';
    }
}
