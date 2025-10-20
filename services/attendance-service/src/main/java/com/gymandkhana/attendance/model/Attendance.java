package com.gymandkhana.attendance.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.CompoundIndex;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Duration;

@Document(collection = "attendances")
@CompoundIndex(name = "user_date_idx", def = "{'userId': 1, 'date': 1}", unique = true)
public class Attendance {
    @Id
    private String id;

    @Indexed
    private String userId;

    @Indexed
    private LocalDate date;

    private LocalDateTime checkInTime;
    private LocalDateTime checkOutTime;
    private Long durationMinutes;

    private AttendanceStatus status;

    @CreatedDate
    private LocalDateTime createdAt;

    // Constructors
    public Attendance() {
        this.status = AttendanceStatus.CHECKED_IN;
    }

    public Attendance(String userId, LocalDate date, LocalDateTime checkInTime) {
        this.userId = userId;
        this.date = date;
        this.checkInTime = checkInTime;
        this.status = AttendanceStatus.CHECKED_IN;
    }

    // Business logic
    public void checkOut(LocalDateTime checkOutTime) {
        this.checkOutTime = checkOutTime;
        this.status = AttendanceStatus.CHECKED_OUT;
        calculateDuration();
    }

    private void calculateDuration() {
        if (checkInTime != null && checkOutTime != null) {
            Duration duration = Duration.between(checkInTime, checkOutTime);
            this.durationMinutes = duration.toMinutes();
        }
    }

    public boolean isCheckedIn() {
        return status == AttendanceStatus.CHECKED_IN;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public LocalDateTime getCheckInTime() { return checkInTime; }
    public void setCheckInTime(LocalDateTime checkInTime) { this.checkInTime = checkInTime; }

    public LocalDateTime getCheckOutTime() { return checkOutTime; }
    public void setCheckOutTime(LocalDateTime checkOutTime) {
        this.checkOutTime = checkOutTime;
        calculateDuration();
    }

    public Long getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(Long durationMinutes) { this.durationMinutes = durationMinutes; }

    public AttendanceStatus getStatus() { return status; }
    public void setStatus(AttendanceStatus status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}