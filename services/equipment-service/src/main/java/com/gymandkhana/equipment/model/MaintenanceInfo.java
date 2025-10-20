package com.gymandkhana.equipment.model;

import java.time.LocalDateTime;

public class MaintenanceInfo {
    private LocalDateTime lastMaintenanceDate;
    private LocalDateTime nextMaintenanceDate;
    private int maintenanceCount;
    private String maintenanceNotes;

    // Constructors
    public MaintenanceInfo() {
        this.maintenanceCount = 0;
    }

    // Getters and Setters
    public LocalDateTime getLastMaintenanceDate() { return lastMaintenanceDate; }
    public void setLastMaintenanceDate(LocalDateTime lastMaintenanceDate) {
        this.lastMaintenanceDate = lastMaintenanceDate;
        // Schedule next maintenance in 30 days
        this.nextMaintenanceDate = lastMaintenanceDate.plusDays(30);
        this.maintenanceCount++;
    }

    public LocalDateTime getNextMaintenanceDate() { return nextMaintenanceDate; }
    public void setNextMaintenanceDate(LocalDateTime nextMaintenanceDate) {
        this.nextMaintenanceDate = nextMaintenanceDate;
    }

    public int getMaintenanceCount() { return maintenanceCount; }
    public void setMaintenanceCount(int maintenanceCount) {
        this.maintenanceCount = maintenanceCount;
    }

    public String getMaintenanceNotes() { return maintenanceNotes; }
    public void setMaintenanceNotes(String maintenanceNotes) {
        this.maintenanceNotes = maintenanceNotes;
    }
}