package com.gymandkhana.rewards.model;

import java.time.LocalDateTime;

public class PointsTransaction {
    private int points;
    private String reason;
    private PointsType type;
    private TransactionType transactionType;
    private LocalDateTime timestamp;

    public PointsTransaction() {
        this.timestamp = LocalDateTime.now();
    }

    public PointsTransaction(int points, String reason, PointsType type,
                             TransactionType transactionType) {
        this();
        this.points = points;
        this.reason = reason;
        this.type = type;
        this.transactionType = transactionType;
    }

    // Getters and Setters
    public int getPoints() { return points; }
    public void setPoints(int points) { this.points = points; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public PointsType getType() { return type; }
    public void setType(PointsType type) { this.type = type; }

    public TransactionType getTransactionType() { return transactionType; }
    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}