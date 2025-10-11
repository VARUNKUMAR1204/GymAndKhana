package com.gymandkhana.rewards.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "user_points")
public class UserPoints {
    @Id
    private String id;

    @Indexed(unique = true)
    private String userId;

    private int totalPoints;
    private int availablePoints;
    private int redeemedPoints;

    private List<PointsTransaction> transactions;
    private List<String> achievementIds;

    private int currentLevel;
    private String currentTier;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public UserPoints() {
        this.totalPoints = 0;
        this.availablePoints = 0;
        this.redeemedPoints = 0;
        this.transactions = new ArrayList<>();
        this.achievementIds = new ArrayList<>();
        this.currentLevel = 1;
        this.currentTier = "Bronze";
    }

    public UserPoints(String userId) {
        this();
        this.userId = userId;
    }

    public void addPoints(int points, String reason, PointsType type) {
        this.totalPoints += points;
        this.availablePoints += points;

        PointsTransaction transaction = new PointsTransaction(
                points, reason, type, TransactionType.CREDIT
        );
        this.transactions.add(transaction);

        updateLevel();
    }

    public void deductPoints(int points, String reason) {
        if (this.availablePoints < points) {
            throw new RuntimeException("Insufficient points");
        }

        this.availablePoints -= points;
        this.redeemedPoints += points;

        PointsTransaction transaction = new PointsTransaction(
                points, reason, PointsType.REDEMPTION, TransactionType.DEBIT
        );
        this.transactions.add(transaction);
    }

    public void addAchievement(String achievementId) {
        if (!this.achievementIds.contains(achievementId)) {
            this.achievementIds.add(achievementId);
        }
    }

    private void updateLevel() {
        if (totalPoints >= 5000) {
            currentLevel = 5;
            currentTier = "Platinum";
        } else if (totalPoints >= 2000) {
            currentLevel = 4;
            currentTier = "Gold";
        } else if (totalPoints >= 1000) {
            currentLevel = 3;
            currentTier = "Silver";
        } else if (totalPoints >= 500) {
            currentLevel = 2;
            currentTier = "Bronze";
        } else {
            currentLevel = 1;
            currentTier = "Starter";
        }
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public int getTotalPoints() { return totalPoints; }
    public void setTotalPoints(int totalPoints) { this.totalPoints = totalPoints; }

    public int getAvailablePoints() { return availablePoints; }
    public void setAvailablePoints(int availablePoints) { this.availablePoints = availablePoints; }

    public int getRedeemedPoints() { return redeemedPoints; }
    public void setRedeemedPoints(int redeemedPoints) { this.redeemedPoints = redeemedPoints; }

    public List<PointsTransaction> getTransactions() { return transactions; }
    public void setTransactions(List<PointsTransaction> transactions) {
        this.transactions = transactions;
    }

    public List<String> getAchievementIds() { return achievementIds; }
    public void setAchievementIds(List<String> achievementIds) {
        this.achievementIds = achievementIds;
    }

    public int getCurrentLevel() { return currentLevel; }
    public void setCurrentLevel(int currentLevel) { this.currentLevel = currentLevel; }

    public String getCurrentTier() { return currentTier; }
    public void setCurrentTier(String currentTier) { this.currentTier = currentTier; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
