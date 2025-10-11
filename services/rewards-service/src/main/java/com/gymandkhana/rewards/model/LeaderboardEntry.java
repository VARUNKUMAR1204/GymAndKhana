package com.gymandkhana.rewards.model;

public class LeaderboardEntry {
    private int rank;
    private String userId;
    private String userName;
    private int points;
    private String tier;

    public LeaderboardEntry() {}

    public LeaderboardEntry(int rank, String userId, String userName, int points, String tier) {
        this.rank = rank;
        this.userId = userId;
        this.userName = userName;
        this.points = points;
        this.tier = tier;
    }

    // Getters and Setters
    public int getRank() { return rank; }
    public void setRank(int rank) { this.rank = rank; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public int getPoints() { return points; }
    public void setPoints(int points) { this.points = points; }

    public String getTier() { return tier; }
    public void setTier(String tier) { this.tier = tier; }
}
