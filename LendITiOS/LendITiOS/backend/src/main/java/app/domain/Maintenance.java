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
@Table(name = "maintenance")
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
    @Column(name = "repairNotes", length = 100)
    private String repairNotes;
    @Column(name = "openedDate")
    private LocalDate openedDate;
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
    protected Maintenance() {}

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
    public Maintenance(String issue, maintStat maintenanceStatus, String repairNotes,
                       LocalDate openedDate, LocalDate completedDate, Asset asset) {
        this.issue = issue;
        this.maintenanceStatus = maintenanceStatus;
        this.repairNotes = repairNotes;
        this.openedDate = openedDate;
        this.completedDate = completedDate;
        this.asset = asset;
    }

    public Integer getMaintenanceId() {
        return maintenanceId;
    }

    public void setMaintenanceId(Integer maintenanceId) {
        this.maintenanceId = maintenanceId;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public maintStat getMaintenanceStatus() {
        return maintenanceStatus;
    }

    public void setMaintenanceStatus(maintStat maintenanceStatus) {
        this.maintenanceStatus = maintenanceStatus;
    }

    // Convenience alias to match alternative spelling patterns
    public maintStat getMaintenaceStatus() {
        return maintenanceStatus;
    }

    public String getRepairNotes() {
        return repairNotes;
    }

    public void setRepairNotes(String repairNotes) {
        this.repairNotes = repairNotes;
    }

    public LocalDate getOpenedDate() {
        return openedDate;
    }

    public void setOpenedDate(LocalDate openedDate) {
        this.openedDate = openedDate;
    }

    // Convenience alias if any service calls getOpenDate()
    public LocalDate getOpenDate() {
        return openedDate;
    }

    public void setOpenDate(LocalDate openedDate) {
        this.openedDate = openedDate;
    }

    public LocalDate getCompletedDate() {
        return completedDate;
    }

    public void setCompletedDate(LocalDate completedDate) {
        this.completedDate = completedDate;
    }

    public Asset getAsset() {
        return asset;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }

    // Convenience alias matching the API's method name style
    public Asset getAssetId() {
        return asset;
    }

    public void setAssetId(Asset asset) {
        this.asset = asset;
    }

    @Override
    public String toString() {
        return "Maintenance{" +
                "maintenanceId=" + maintenanceId +
                ", issue='" + issue + '\'' +
                ", maintenanceStatus=" + maintenanceStatus +
                ", repairNotes='" + repairNotes + '\'' +
                ", openedDate=" + openedDate +
                ", completedDate=" + completedDate +
                '}';
    }
}