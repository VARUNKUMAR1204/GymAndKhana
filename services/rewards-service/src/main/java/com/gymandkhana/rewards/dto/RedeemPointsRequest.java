package com.gymandkhana.rewards.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class RedeemPointsRequest {
    @NotBlank(message = "User ID is required")
    private String userId;

    @Min(value = 1, message = "Points must be at least 1")
    private int points;

    @NotBlank(message = "Reason is required")
    private String reason;

    // Getters and Setters
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public int getPoints() { return points; }
    public void setPoints(int points) { this.points = points; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
}