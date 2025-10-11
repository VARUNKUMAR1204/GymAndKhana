package com.gymandkhana.rewards.model;

import java.util.List;

public class Leaderboard {
    private List<LeaderboardEntry> entries;
    private String period;  // WEEKLY, MONTHLY, ALL_TIME

    public Leaderboard() {}

    public Leaderboard(List<LeaderboardEntry> entries, String period) {
        this.entries = entries;
        this.period = period;
    }

    // Getters and Setters
    public List<LeaderboardEntry> getEntries() { return entries; }
    public void setEntries(List<LeaderboardEntry> entries) { this.entries = entries; }

    public String getPeriod() { return period; }
    public void setPeriod(String period) { this.period = period; }
}