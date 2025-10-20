package com.gymandkhana.equipment.dto;

import com.gymandkhana.equipment.model.EquipmentStatus;
import java.util.Map;

public class EquipmentStatsResponse {
    private int totalEquipments;
    private int availableCount;
    private int inUseCount;
    private int underMaintenanceCount;
    private int outOfOrderCount;
    private Map<String, Integer> equipmentByType;
    private Map<String, Integer> usageByLocation;
    private int totalBookingsToday;

    // Getters and Setters
    public int getTotalEquipments() { return totalEquipments; }
    public void setTotalEquipments(int totalEquipments) {
        this.totalEquipments = totalEquipments;
    }

    public int getAvailableCount() { return availableCount; }
    public void setAvailableCount(int availableCount) { this.availableCount = availableCount; }

    public int getInUseCount() { return inUseCount; }
    public void setInUseCount(int inUseCount) { this.inUseCount = inUseCount; }

    public int getUnderMaintenanceCount() { return underMaintenanceCount; }
    public void setUnderMaintenanceCount(int underMaintenanceCount) {
        this.underMaintenanceCount = underMaintenanceCount;
    }

    public int getOutOfOrderCount() { return outOfOrderCount; }
    public void setOutOfOrderCount(int outOfOrderCount) {
        this.outOfOrderCount = outOfOrderCount;
    }

    public Map<String, Integer> getEquipmentByType() { return equipmentByType; }
    public void setEquipmentByType(Map<String, Integer> equipmentByType) {
        this.equipmentByType = equipmentByType;
    }

    public Map<String, Integer> getUsageByLocation() { return usageByLocation; }
    public void setUsageByLocation(Map<String, Integer> usageByLocation) {
        this.usageByLocation = usageByLocation;
    }

    public int getTotalBookingsToday() { return totalBookingsToday; }
    public void setTotalBookingsToday(int totalBookingsToday) {
        this.totalBookingsToday = totalBookingsToday;
    }
}