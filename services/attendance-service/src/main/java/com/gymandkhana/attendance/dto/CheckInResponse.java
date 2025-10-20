package com.gymandkhana.attendance.dto;

import java.time.LocalDateTime;

public class CheckInResponse {
    private String attendanceId;
    private String userId;
    private LocalDateTime checkInTime;
    private String message;
    private StreakResponse streakInfo;

    // Constructors
    public CheckInResponse() {}

    public CheckInResponse(String attendanceId, String userId, LocalDateTime checkInTime,
                           String message, StreakResponse streakInfo) {
        this.attendanceId = attendanceId;
        this.userId = userId;
        this.checkInTime = checkInTime;
        this.message = message;
        this.streakInfo = streakInfo;
    }

    // Getters and Setters
    public String getAttendanceId() { return attendanceId; }
    public void setAttendanceId(String attendanceId) { this.attendanceId = attendanceId; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public LocalDateTime getCheckInTime() { return checkInTime; }
    public void setCheckInTime(LocalDateTime checkInTime) { this.checkInTime = checkInTime; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public StreakResponse getStreakInfo() { return streakInfo; }
    public void setStreakInfo(StreakResponse streakInfo) { this.streakInfo = streakInfo; }
}