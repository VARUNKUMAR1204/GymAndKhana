package com.gymandkhana.rewards.service;

import com.gymandkhana.rewards.dto.AddPointsRequest;
import com.gymandkhana.rewards.dto.RedeemPointsRequest;
import com.gymandkhana.rewards.model.*;
import com.gymandkhana.rewards.repository.UserPointsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RewardsService {

    @Autowired
    private UserPointsRepository userPointsRepository;

    public UserPoints addPoints(AddPointsRequest request) {
        UserPoints userPoints = userPointsRepository.findByUserId(request.getUserId())
                .orElse(new UserPoints(request.getUserId()));

        userPoints.addPoints(request.getPoints(), request.getReason(), request.getType());

        return userPointsRepository.save(userPoints);
    }

    public UserPoints redeemPoints(RedeemPointsRequest request) {
        UserPoints userPoints = userPointsRepository.findByUserId(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User points not found"));

        userPoints.deductPoints(request.getPoints(), request.getReason());

        return userPointsRepository.save(userPoints);
    }

    public UserPoints getUserPoints(String userId) {
        return userPointsRepository.findByUserId(userId)
                .orElse(new UserPoints(userId));
    }

    public List<UserPoints> getAllUserPoints() {
        return userPointsRepository.findAll();
    }

    public Leaderboard getLeaderboard(int limit) {
        List<UserPoints> allUsers = userPointsRepository.findAllByOrderByTotalPointsDesc();

        List<LeaderboardEntry> entries = new java.util.ArrayList<>();
        int rank = 1;

        for (UserPoints user : allUsers) {
            if (rank > limit) break;

            LeaderboardEntry entry = new LeaderboardEntry(
                    rank,
                    user.getUserId(),
                    "User " + user.getUserId().substring(0, Math.min(8, user.getUserId().length())),
                    user.getTotalPoints(),
                    user.getCurrentTier()
            );
            entries.add(entry);
            rank++;
        }

        return new Leaderboard(entries, "ALL_TIME");
    }

    public UserPoints addAchievementToUser(String userId, String achievementId) {
        UserPoints userPoints = getUserPoints(userId);
        userPoints.addAchievement(achievementId);
        return userPointsRepository.save(userPoints);
    }

    public int calculateStreakBonus(int streakDays) {
        if (streakDays >= 30) return 100;
        if (streakDays >= 14) return 50;
        if (streakDays >= 7) return 25;
        if (streakDays >= 3) return 10;
        return 0;
    }
}