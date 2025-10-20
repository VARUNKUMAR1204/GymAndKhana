package com.gymandkhana.equipment.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.LocalDateTime;

@Document(collection = "equipments")
public class Equipment {
    @Id
    private String id;

    @Indexed(unique = true)
    private String equipmentCode;

    private String name;
    private String description;
    private EquipmentType type;
    private EquipmentStatus status;

    private String location;
    private String imageUrl;

    private MaintenanceInfo maintenanceInfo;
    private UsageStats usageStats;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    // Constructors
    public Equipment() {
        this.status = EquipmentStatus.AVAILABLE;
        this.maintenanceInfo = new MaintenanceInfo();
        this.usageStats = new UsageStats();
    }

    public Equipment(String equipmentCode, String name, EquipmentType type) {
        this();
        this.equipmentCode = equipmentCode;
        this.name = name;
        this.type = type;
    }

    // Business Methods
    public boolean isAvailable() {
        return status == EquipmentStatus.AVAILABLE;
    }

    public void markInUse(String userId) {
        this.status = EquipmentStatus.IN_USE;
        if (this.usageStats != null) {
            this.usageStats.incrementUsageCount();
            this.usageStats.setLastUsedBy(userId);
            this.usageStats.setLastUsedAt(LocalDateTime.now());
        }
    }

    public void markAvailable() {
        this.status = EquipmentStatus.AVAILABLE;
    }

    public void markUnderMaintenance() {
        this.status = EquipmentStatus.UNDER_MAINTENANCE;
        if (this.maintenanceInfo != null) {
            this.maintenanceInfo.setLastMaintenanceDate(LocalDateTime.now());
        }
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getEquipmentCode() { return equipmentCode; }
    public void setEquipmentCode(String equipmentCode) { this.equipmentCode = equipmentCode; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public EquipmentType getType() { return type; }
    public void setType(EquipmentType type) { this.type = type; }

    public EquipmentStatus getStatus() { return status; }
    public void setStatus(EquipmentStatus status) { this.status = status; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public MaintenanceInfo getMaintenanceInfo() { return maintenanceInfo; }
    public void setMaintenanceInfo(MaintenanceInfo maintenanceInfo) {
        this.maintenanceInfo = maintenanceInfo;
    }

    public UsageStats getUsageStats() { return usageStats; }
    public void setUsageStats(UsageStats usageStats) { this.usageStats = usageStats; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
