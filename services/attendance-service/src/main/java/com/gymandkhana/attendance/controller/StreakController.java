package com.gymandkhana.attendance.controller;

import com.gymandkhana.attendance.dto.StreakResponse;
import com.gymandkhana.attendance.service.StreakCalculationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/streaks")
@CrossOrigin(origins = "*")
public class StreakController {

    @Autowired
    private StreakCalculationService streakCalculationService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserStreak(@PathVariable String userId) {
        try {
            StreakResponse streak = streakCalculationService.calculateStreak(userId);

            Map<String, Object> response = new HashMap<>();
            response.put("userId", userId);
            response.put("streak", streak);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping("/user/{userId}/summary")
    public ResponseEntity<?> getUserStreakSummary(@PathVariable String userId) {
        try {
            StreakResponse streak = streakCalculationService.calculateStreak(userId);

            Map<String, Object> summary = new HashMap<>();
            summary.put("userId", userId);
            summary.put("currentStreak", streak.getCurrentStreak() + " days");
            summary.put("longestStreak", streak.getLongestStreak() + " days");
            summary.put("totalDays", streak.getTotalAttendanceDays());
            summary.put("streakBonus", streak.getStreakBonus() + " points");
            summary.put("message", streak.getStreakMessage());
            summary.put("isActiveToday", streak.isActiveToday());

            return ResponseEntity.ok(summary);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
}