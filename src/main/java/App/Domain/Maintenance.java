package App.Domain;

import jakarta.persistence.*;

import java.time.LocalDate;
@Entity
@Table(name= "maintenance")
public class Maintenance {

    @Id
    private String maintenanceId;
    private String issue;
    @Enumerated(EnumType.STRING)
    private maintStat MaintenanceStatus;
    private String repairNotes;
    private LocalDate openDate;
    private LocalDate completedDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asset_id")
    private Asset asset;

    protected Maintenance() {

    }

    public Maintenance(String maintenanceId, String issue, maintStat MaintenanceStatus, String repairNotes, LocalDate openDate, LocalDate completedDate, Asset asset) {
        this.maintenanceId = maintenanceId;
        this.issue = issue;
        this.MaintenanceStatus = MaintenanceStatus;
        this.repairNotes = repairNotes;
        this.openDate = openDate;
        this.completedDate = completedDate;
        this.asset = asset;
    }

    public String getMaintenanceId() {
        return maintenanceId;
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
