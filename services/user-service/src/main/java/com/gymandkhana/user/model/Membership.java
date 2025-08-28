package com.gymandkhana.user.model;

import java.time.LocalDateTime;

public class Membership {
    private MembershipType type;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Double monthlyFee;
    private boolean isActive;


    public Membership() {}

    public Membership(MembershipType type, LocalDateTime startDate,
                      LocalDateTime endDate, Double monthlyFee) {
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
        this.monthlyFee = monthlyFee;
        this.isActive = true;
    }

    // Getters and Setters
    public MembershipType getType() { return type; }
    public void setType(MembershipType type) { this.type = type; }

    public LocalDateTime getStartDate() { return startDate; }
    public void setStartDate(LocalDateTime startDate) { this.startDate = startDate; }

    public LocalDateTime getEndDate() { return endDate; }
    public void setEndDate(LocalDateTime endDate) { this.endDate = endDate; }

    public Double getMonthlyFee() { return monthlyFee; }
    public void setMonthlyFee(Double monthlyFee) { this.monthlyFee = monthlyFee; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(endDate);
    }
}