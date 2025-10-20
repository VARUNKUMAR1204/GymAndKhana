package com.gymandkhana.attendance.dto;

import jakarta.validation.constraints.NotBlank;

public class CheckInRequest {
    @NotBlank(message = "User ID is required")
    private String userId;

    // Constructors
    public CheckInRequest() {}

    public CheckInRequest(String userId) {
        this.userId = userId;
    }

    // Getters and Setters
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
}