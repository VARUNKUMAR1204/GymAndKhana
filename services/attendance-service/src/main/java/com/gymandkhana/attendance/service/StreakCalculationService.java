package com.gymandkhana.attendance.service;

import com.gymandkhana.attendance.dto.StreakResponse;
import com.gymandkhana.attendance.model.Attendance;
import com.gymandkhana.attendance.model.StreakInfo;
import com.gymandkhana.attendance.repository.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class StreakCalculationService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    public StreakResponse calculateStreak(String userId) {
        List<Attendance> attendances = attendanceRepository.findByUserIdOrderByDateDesc(userId);

        if (attendances.isEmpty()) {
            return createEmptyStreakResponse();
        }

        StreakInfo streakInfo = calculateStreakInfo(attendances);
        return convertToStreakResponse(streakInfo);
    }

    private StreakInfo calculateStreakInfo(List<Attendance> attendances) {
        int currentStreak = 0;
        int longestStreak = 0;
        int tempStreak = 1;
        LocalDate today = LocalDate.now();
        LocalDate lastDate = null;

        // Sort by date ascending for streak calculation
        attendances.sort((a, b) -> a.getDate().compareTo(b.getDate()));

        for (int i = 0; i < attendances.size(); i++) {
            LocalDate currentDate = attendances.get(i).getDate();

            if (lastDate == null) {
                lastDate = currentDate;
                tempStreak = 1;
            } else {
                long daysDiff = ChronoUnit.DAYS.between(lastDate, currentDate);

                if (daysDiff == 1) {
                    // Consecutive day
                    tempStreak++;
                } else {
                    // Streak broken
                    longestStreak = Math.max(longestStreak, tempStreak);
                    tempStreak = 1;
                }
                lastDate = currentDate;
            }
        }

        // Update longest streak one final time
        longestStreak = Math.max(longestStreak, tempStreak);

        // Calculate current streak (from most recent attendance)
        lastDate = attendances.get(attendances.size() - 1).getDate();
        long daysSinceLastAttendance = ChronoUnit.DAYS.between(lastDate, today);

        if (daysSinceLastAttendance == 0) {
            // Attended today
            currentStreak = tempStreak;
        } else if (daysSinceLastAttendance == 1) {
            // Last attendance was yesterday, streak continues if they attend today
            currentStreak = tempStreak;
        } else {
            // Streak broken
            currentStreak = 0;
        }

        boolean isActiveToday = daysSinceLastAttendance == 0;
        int totalDays = attendances.size();

        return new StreakInfo(currentStreak, longestStreak, lastDate, totalDays, isActiveToday);
    }

    private StreakResponse createEmptyStreakResponse() {
        StreakResponse response = new StreakResponse();
        response.setCurrentStreak(0);
        response.setLongestStreak(0);
        response.setLastAttendanceDate(null);
        response.setTotalAttendanceDays(0);
        response.setActiveToday(false);
        response.setStreakBonus(0);
        response.setStreakMessage("Start your streak today!");
        return response;
    }

    private StreakResponse convertToStreakResponse(StreakInfo info) {
        StreakResponse response = new StreakResponse();
        response.setCurrentStreak(info.getCurrentStreak());
        response.setLongestStreak(info.getLongestStreak());
        response.setLastAttendanceDate(info.getLastAttendanceDate());
        response.setTotalAttendanceDays(info.getTotalAttendanceDays());
        response.setActiveToday(info.isActiveToday());
        response.setStreakBonus(info.getStreakBonus());
        response.setStreakMessage(generateStreakMessage(info.getCurrentStreak()));
        return response;
    }

    private String generateStreakMessage(int streak) {
        if (streak >= 30) return "🔥 Incredible! You're on fire with a 30+ day streak!";
        if (streak >= 14) return "💪 Outstanding! 2 weeks of consistency!";
        if (streak >= 7) return "⭐ Fantastic! 1 week streak achieved!";
        if (streak >= 3) return "👍 Great job! Keep the momentum going!";
        if (streak >= 1) return "🎯 Good start! Build your streak!";
        return "Start your fitness journey today!";
    }
}