package com.gymandkhana.equipment.model;

import java.time.LocalDateTime;

public class UsageStats {
    private int totalUsageCount;
    private String lastUsedBy;
    private LocalDateTime lastUsedAt;
    private int peakHourUsageCount;

    // Constructors
    public UsageStats() {
        this.totalUsageCount = 0;
        this.peakHourUsageCount = 0;
    }

    public void incrementUsageCount() {
        this.totalUsageCount++;

        // Track peak hour usage (6 AM - 10 AM and 5 PM - 9 PM)
        if (lastUsedAt != null) {
            int hour = lastUsedAt.getHour();
            if ((hour >= 6 && hour < 10) || (hour >= 17 && hour < 21)) {
                this.peakHourUsageCount++;
            }
        }
    }

    // Getters and Setters
    public int getTotalUsageCount() { return totalUsageCount; }
    public void setTotalUsageCount(int totalUsageCount) {
        this.totalUsageCount = totalUsageCount;
    }

    public String getLastUsedBy() { return lastUsedBy; }
    public void setLastUsedBy(String lastUsedBy) { this.lastUsedBy = lastUsedBy; }

    public LocalDateTime getLastUsedAt() { return lastUsedAt; }
    public void setLastUsedAt(LocalDateTime lastUsedAt) { this.lastUsedAt = lastUsedAt; }

    public int getPeakHourUsageCount() { return peakHourUsageCount; }
    public void setPeakHourUsageCount(int peakHourUsageCount) {
        this.peakHourUsageCount = peakHourUsageCount;
    }
}