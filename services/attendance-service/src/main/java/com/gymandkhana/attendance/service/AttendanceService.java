package com.gymandkhana.attendance.service;

import com.gymandkhana.attendance.dto.*;
import com.gymandkhana.attendance.exception.AlreadyCheckedInException;
import com.gymandkhana.attendance.exception.AttendanceNotFoundException;
import com.gymandkhana.attendance.model.Attendance;
import com.gymandkhana.attendance.model.AttendanceStatus;
import com.gymandkhana.attendance.repository.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private StreakCalculationService streakCalculationService;

    public CheckInResponse checkIn(CheckInRequest request) {
        String userId = request.getUserId();
        LocalDate today = LocalDate.now();

        // Check if already checked in today
        Optional<Attendance> existingAttendance = attendanceRepository.findByUserIdAndDate(userId, today);
        if (existingAttendance.isPresent() && existingAttendance.get().isCheckedIn()) {
            throw new AlreadyCheckedInException("User already checked in today");
        }

        // Create new attendance record
        Attendance attendance = new Attendance(userId, today, LocalDateTime.now());
        Attendance savedAttendance = attendanceRepository.save(attendance);

        // Calculate streak info
        StreakResponse streakInfo = streakCalculationService.calculateStreak(userId);

        // Create response
        CheckInResponse response = new CheckInResponse();
        response.setAttendanceId(savedAttendance.getId());
        response.setUserId(userId);
        response.setCheckInTime(savedAttendance.getCheckInTime());
        response.setMessage("Check-in successful! " + getStreakMessage(streakInfo.getCurrentStreak()));
        response.setStreakInfo(streakInfo);

        return response;
    }

    public CheckInResponse checkOut(String userId) {
        LocalDate today = LocalDate.now();

        Attendance attendance = attendanceRepository.findByUserIdAndDate(userId, today)
                .orElseThrow(() -> new AttendanceNotFoundException("No check-in found for today"));

        if (!attendance.isCheckedIn()) {
            throw new RuntimeException("User already checked out");
        }

        // Update checkout time
        attendance.checkOut(LocalDateTime.now());
        Attendance updatedAttendance = attendanceRepository.save(attendance);

        // Calculate streak info
        StreakResponse streakInfo = streakCalculationService.calculateStreak(userId);

        // Create response
        CheckInResponse response = new CheckInResponse();
        response.setAttendanceId(updatedAttendance.getId());
        response.setUserId(userId);
        response.setCheckInTime(updatedAttendance.getCheckOutTime());
        response.setMessage("Check-out successful! You spent " + updatedAttendance.getDurationMinutes() + " minutes today.");
        response.setStreakInfo(streakInfo);

        return response;
    }

    public List<Attendance> getUserAttendanceHistory(String userId) {
        return attendanceRepository.findByUserIdOrderByDateDesc(userId);
    }

    public List<Attendance> getUserAttendanceByDateRange(String userId, LocalDate startDate, LocalDate endDate) {
        return attendanceRepository.findByUserIdAndDateBetweenOrderByDateDesc(userId, startDate, endDate);
    }

    public AttendanceStatsResponse getUserAttendanceStats(String userId) {
        List<Attendance> allAttendances = attendanceRepository.findByUserIdOrderByDateDesc(userId);

        AttendanceStatsResponse stats = new AttendanceStatsResponse();

        if (allAttendances.isEmpty()) {
            stats.setTotalDays(0);
            stats.setPresentDays(0);
            stats.setAbsentDays(0);
            stats.setAttendancePercentage(0.0);
            return stats;
        }

        // Calculate basic stats
        int presentDays = (int) allAttendances.stream()
                .filter(a -> a.getStatus() == AttendanceStatus.CHECKED_OUT)
                .count();

        stats.setPresentDays(presentDays);
        stats.setTotalDays(allAttendances.size());
        stats.setAbsentDays(allAttendances.size() - presentDays);

        // Calculate attendance percentage
        if (allAttendances.size() > 0) {
            stats.setAttendancePercentage((presentDays * 100.0) / allAttendances.size());
        }

        // Calculate total time spent
        Long totalMinutes = allAttendances.stream()
                .filter(a -> a.getDurationMinutes() != null)
                .mapToLong(Attendance::getDurationMinutes)
                .sum();
        stats.setTotalMinutesSpent(totalMinutes);

        // Calculate average minutes per visit
        long completedVisits = allAttendances.stream()
                .filter(a -> a.getDurationMinutes() != null)
                .count();
        if (completedVisits > 0) {
            stats.setAverageMinutesPerVisit(totalMinutes / completedVisits);
        }

        // Monthly attendance
        Map<String, Integer> monthlyAttendance = allAttendances.stream()
                .collect(Collectors.groupingBy(
                        a -> a.getDate().getMonth().toString(),
                        Collectors.collectingAndThen(Collectors.counting(), Long::intValue)
                ));
        stats.setMonthlyAttendance(monthlyAttendance);

        // Weekly attendance (day of week)
        Map<Integer, Integer> weeklyAttendance = allAttendances.stream()
                .collect(Collectors.groupingBy(
                        a -> a.getDate().getDayOfWeek().getValue(),
                        Collectors.collectingAndThen(Collectors.counting(), Long::intValue)
                ));
        stats.setWeeklyAttendance(weeklyAttendance);

        return stats;
    }

    public long getTodayActiveCount() {
        return attendanceRepository.countActiveAttendancesByDate(LocalDate.now());
    }

    public List<Attendance> getTodayAllAttendances() {
        return attendanceRepository.findAllByDate(LocalDate.now());
    }

    public boolean isUserCheckedInToday(String userId) {
        Optional<Attendance> attendance = attendanceRepository.findByUserIdAndDate(userId, LocalDate.now());
        return attendance.isPresent() && attendance.get().isCheckedIn();
    }

    private String getStreakMessage(int streak) {
        if (streak >= 30) return "🔥 Amazing! 30-day streak!";
        if (streak >= 14) return "💪 Great! 2-week streak!";
        if (streak >= 7) return "⭐ Awesome! 7-day streak!";
        if (streak >= 3) return "👍 Keep it up! 3-day streak!";
        if (streak >= 1) return "🎯 Good start!";
        return "Welcome!";
    }
}