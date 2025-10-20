package com.gymandkhana.attendance.model;

import java.time.LocalDate;

public class StreakInfo {
    private int currentStreak;
    private int longestStreak;
    private LocalDate lastAttendanceDate;
    private int totalAttendanceDays;
    private boolean isActiveToday;
    private int streakBonus;

    // Constructors
    public StreakInfo() {}

    public StreakInfo(int currentStreak, int longestStreak, LocalDate lastAttendanceDate,
                      int totalAttendanceDays, boolean isActiveToday) {
        this.currentStreak = currentStreak;
        this.longestStreak = longestStreak;
        this.lastAttendanceDate = lastAttendanceDate;
        this.totalAttendanceDays = totalAttendanceDays;
        this.isActiveToday = isActiveToday;
        this.streakBonus = calculateStreakBonus(currentStreak);
    }

    private int calculateStreakBonus(int streak) {
        if (streak >= 30) return 100;
        if (streak >= 14) return 50;
        if (streak >= 7) return 25;
        if (streak >= 3) return 10;
        return 0;
    }

    // Getters and Setters
    public int getCurrentStreak() { return currentStreak; }
    public void setCurrentStreak(int currentStreak) {
        this.currentStreak = currentStreak;
        this.streakBonus = calculateStreakBonus(currentStreak);
    }

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
}
