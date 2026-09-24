package app.domain;

import jakarta.persistence.*;

import java.time.LocalDate;
@Entity
@Table(name= "maintenance")
public class Maintenance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maintenanceId;


    @Column(length = 40)
    private String issue;


    @Enumerated(EnumType.STRING)
    @Column(name = "maintenanceStatus", length = 15)
    private maintStat MaintenanceStatus;

    @Column(length = 100)
    private String repairNotes;

    @Column(name = "openedDate")
    private LocalDate openDate;


    private LocalDate completedDate;


    @ManyToOne
    @JoinColumn(name = "assetId")
    private Asset asset;

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

    public Integer getMaintenanceId() {
        return maintenanceId;
    }

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
