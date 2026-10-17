package com.example.assettracker;

import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Maintenance {

    @Id
    @Column(name = "maintenanceId")
    private Long maintenanceId;

    @ManyToOne
    @JoinColumn(name = "assetId")
    private Asset asset;

    private String issue;

    @Column(name = "maintenanceStatus")
    private String maintenanceStatus;

    @Column(name = "repairNotes")
    private String repairNotes;

    @Column(name = "openedDate")
    private LocalDate openedDate;

    @Column(name = "completedDate")
    private LocalDate completedDate;

    public Maintenance() {}

    public Maintenance(Long maintenanceId, Asset asset, String issue, String maintenanceStatus,
                        String repairNotes, LocalDate openedDate, LocalDate completedDate) {
        this.maintenanceId = maintenanceId;
        this.asset = asset;
        this.issue = issue;
        this.maintenanceStatus = maintenanceStatus;
        this.repairNotes = repairNotes;
        this.openedDate = openedDate;
        this.completedDate = completedDate;
    }

    public Long getMaintenanceId() { return maintenanceId; }
    public Asset getAsset() { return asset; }
    public String getIssue() { return issue; }
    public String getMaintenanceStatus() { return maintenanceStatus; }
    public String getRepairNotes() { return repairNotes; }
    public LocalDate getOpenedDate() { return openedDate; }
    public LocalDate getCompletedDate() { return completedDate; }
}
