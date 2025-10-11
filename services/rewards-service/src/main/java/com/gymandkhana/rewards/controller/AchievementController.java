package com.gymandkhana.rewards.controller;

import com.gymandkhana.rewards.model.Achievement;
import com.gymandkhana.rewards.model.AchievementType;
import com.gymandkhana.rewards.service.AchievementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/achievements")
@CrossOrigin(origins = "*")
public class AchievementController {

    @Autowired
    private AchievementService achievementService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllAchievements() {
        try {
            List<Achievement> achievements = achievementService.getAllAchievements();

            Map<String, Object> response = new HashMap<>();
            response.put("total", achievements.size());
            response.put("achievements", achievements);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<?> getAchievementsByType(@PathVariable AchievementType type) {
        try {
            List<Achievement> achievements = achievementService.getAchievementsByType(type);

            Map<String, Object> response = new HashMap<>();
            response.put("type", type);
            response.put("count", achievements.size());
            response.put("achievements", achievements);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAchievementById(@PathVariable String id) {
        try {
            Achievement achievement = achievementService.getAchievementById(id);
            return ResponseEntity.ok(achievement);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @PostMapping("/initialize")
    public ResponseEntity<?> initializeDefaults() {
        try {
            achievementService.initializeDefaultAchievements();
            Map<String, String> response = new HashMap<>();
            response.put("message", "Default achievements initialized");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
}