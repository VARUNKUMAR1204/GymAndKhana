package com.gymandkhana.attendance.dto;

import java.util.Map;

public class AttendanceStatsResponse {
    private int totalDays;
    private int presentDays;
    private int absentDays;
    private double attendancePercentage;
    private Long totalMinutesSpent;
    private Long averageMinutesPerVisit;
    private Map<String, Integer> monthlyAttendance;
    private Map<Integer, Integer> weeklyAttendance;

    // Constructors
    public AttendanceStatsResponse() {
    }

    // Getters and Setters
    public int getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(int totalDays) {
        this.totalDays = totalDays;
    }

    public int getPresentDays() {
        return presentDays;
    }

    public void setPresentDays(int presentDays) {
        this.presentDays = presentDays;
    }

    public int getAbsentDays() {
        return absentDays;
    }

    public void setAbsentDays(int absentDays) {
        this.absentDays = absentDays;
    }

    public double getAttendancePercentage() {
        return attendancePercentage;
    }

    public void setAttendancePercentage(double attendancePercentage) {
        this.attendancePercentage = attendancePercentage;
    }

    public Long getTotalMinutesSpent() {
        return totalMinutesSpent;
    }

    public void setTotalMinutesSpent(Long totalMinutesSpent) {
        this.totalMinutesSpent = totalMinutesSpent;
    }

    public Long getAverageMinutesPerVisit() {
        return averageMinutesPerVisit;
    }

    public void setAverageMinutesPerVisit(Long averageMinutesPerVisit) {
        this.averageMinutesPerVisit = averageMinutesPerVisit;
    }

    public Map<String, Integer> getMonthlyAttendance() {
        return monthlyAttendance;
    }

    public void setMonthlyAttendance(Map<String, Integer> monthlyAttendance) {
        this.monthlyAttendance = monthlyAttendance;
    }

    public Map<Integer, Integer> getWeeklyAttendance() {
        return weeklyAttendance;
    }

    public void setWeeklyAttendance(Map<Integer, Integer> weeklyAttendance) {
        this.weeklyAttendance = weeklyAttendance;
    }
}