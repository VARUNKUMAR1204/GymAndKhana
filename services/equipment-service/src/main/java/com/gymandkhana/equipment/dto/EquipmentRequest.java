package com.gymandkhana.equipment.dto;

import com.gymandkhana.equipment.model.EquipmentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class EquipmentRequest {
    @NotBlank(message = "Equipment code is required")
    private String equipmentCode;

    @NotBlank(message = "Equipment name is required")
    private String name;

    private String description;

    @NotNull(message = "Equipment type is required")
    private EquipmentType type;

    private String location;
    private String imageUrl;

    // Getters and Setters
    public String getEquipmentCode() { return equipmentCode; }
    public void setEquipmentCode(String equipmentCode) { this.equipmentCode = equipmentCode; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public EquipmentType getType() { return type; }
    public void setType(EquipmentType type) { this.type = type; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}