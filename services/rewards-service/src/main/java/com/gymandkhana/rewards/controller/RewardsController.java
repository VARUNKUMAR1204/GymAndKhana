package com.gymandkhana.rewards.controller;

import com.gymandkhana.rewards.dto.AddPointsRequest;
import com.gymandkhana.rewards.dto.RedeemPointsRequest;
import com.gymandkhana.rewards.model.Leaderboard;
import com.gymandkhana.rewards.model.UserPoints;
import com.gymandkhana.rewards.service.RewardsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/rewards")
@CrossOrigin(origins = "*")
public class RewardsController {

    @Autowired
    private RewardsService rewardsService;

    @PostMapping("/add-points")
    public ResponseEntity<?> addPoints(@Valid @RequestBody AddPointsRequest request) {
        try {
            UserPoints userPoints = rewardsService.addPoints(request);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Points added successfully");
            response.put("userPoints", userPoints);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @PostMapping("/redeem-points")
    public ResponseEntity<?> redeemPoints(@Valid @RequestBody RedeemPointsRequest request) {
        try {
            UserPoints userPoints = rewardsService.redeemPoints(request);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Points redeemed successfully");
            response.put("userPoints", userPoints);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserPoints(@PathVariable String userId) {
        try {
            UserPoints userPoints = rewardsService.getUserPoints(userId);
            return ResponseEntity.ok(userPoints);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping("/leaderboard")
    public ResponseEntity<?> getLeaderboard(@RequestParam(defaultValue = "10") int limit) {
        try {
            Leaderboard leaderboard = rewardsService.getLeaderboard(limit);
            return ResponseEntity.ok(leaderboard);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @PostMapping("/user/{userId}/achievement/{achievementId}")
    public ResponseEntity<?> addAchievement(@PathVariable String userId,
                                            @PathVariable String achievementId) {
        try {
            UserPoints userPoints = rewardsService.addAchievementToUser(userId, achievementId);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Achievement added successfully");
            response.put("userPoints", userPoints);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllUserPoints() {
        try {
            List<UserPoints> allPoints = rewardsService.getAllUserPoints();

            Map<String, Object> response = new HashMap<>();
            response.put("total", allPoints.size());
            response.put("userPoints", allPoints);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
}