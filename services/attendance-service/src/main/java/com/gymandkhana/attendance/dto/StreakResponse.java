package com.gymandkhana.attendance.dto;

import java.time.LocalDate;

public class StreakResponse {
    private int currentStreak;
    private int longestStreak;
    private LocalDate lastAttendanceDate;
    private int totalAttendanceDays;
    private boolean isActiveToday;
    private int streakBonus;
    private String streakMessage;

    // Constructors
    public StreakResponse() {}

    // Getters and Setters
    public int getCurrentStreak() { return currentStreak; }
    public void setCurrentStreak(int currentStreak) { this.currentStreak = currentStreak; }

    public int getLongestStreak() { return longestStreak; }
    public void setLongestStreak(int longestStreak) { this.longestStreak = longestStreak; }

    public LocalDate getLastAttendanceDate() { return lastAttendanceDate; }
    public void setLastAttendanceDate(LocalDate lastAttendanceDate) {
        this.lastAttendanceDate = lastAttendanceDate;
    }

    public int getTotalAttendanceDays() { return totalAttendanceDays; }
    public void setTotalAttendanceDays(int totalAttendanceDays) {
        this.totalAttendanceDays = totalAttendanceDays;
    }

    public boolean isActiveToday() { return isActiveToday; }
    public void setActiveToday(boolean activeToday) { isActiveToday = activeToday; }

    public int getStreakBonus() { return streakBonus; }
    public void setStreakBonus(int streakBonus) { this.streakBonus = streakBonus; }

    public String getStreakMessage() { return streakMessage; }
    public void setStreakMessage(String streakMessage) { this.streakMessage = streakMessage; }
}