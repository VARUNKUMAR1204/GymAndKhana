package com.gymandkhana.rewards.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "achievements")
public class Achievement {
    @Id
    private String id;

    private String name;
    private String description;
    private AchievementType type;
    private int requiredValue;
    private int pointsReward;
    private String badgeIcon;
    private LocalDateTime createdAt;

    public Achievement() {
        this.createdAt = LocalDateTime.now();
    }

    public Achievement(String name, String description, AchievementType type,
                       int requiredValue, int pointsReward) {
        this();
        this.name = name;
        this.description = description;
        this.type = type;
        this.requiredValue = requiredValue;
        this.pointsReward = pointsReward;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public AchievementType getType() { return type; }
    public void setType(AchievementType type) { this.type = type; }

    public int getRequiredValue() { return requiredValue; }
    public void setRequiredValue(int requiredValue) { this.requiredValue = requiredValue; }

    public int getPointsReward() { return pointsReward; }
    public void setPointsReward(int pointsReward) { this.pointsReward = pointsReward; }

    public String getBadgeIcon() { return badgeIcon; }
    public void setBadgeIcon(String badgeIcon) { this.badgeIcon = badgeIcon; }

    public LocalDateTime getCreatedAt() { return createdAt; }
}