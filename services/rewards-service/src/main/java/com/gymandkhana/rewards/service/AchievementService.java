package com.gymandkhana.rewards.service;

import com.gymandkhana.rewards.model.Achievement;
import com.gymandkhana.rewards.model.AchievementType;
import com.gymandkhana.rewards.repository.AchievementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AchievementService {

    @Autowired
    private AchievementRepository achievementRepository;

    public Achievement createAchievement(Achievement achievement) {
        return achievementRepository.save(achievement);
    }

    public List<Achievement> getAllAchievements() {
        return achievementRepository.findAll();
    }

    public List<Achievement> getAchievementsByType(AchievementType type) {
        return achievementRepository.findByType(type);
    }

    public Achievement getAchievementById(String id) {
        return achievementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Achievement not found"));
    }

    public void initializeDefaultAchievements() {
        if (achievementRepository.count() == 0) {
            achievementRepository.save(new Achievement(
                    "First Day", "Complete your first check-in",
                    AchievementType.ATTENDANCE, 1, 10
            ));
            achievementRepository.save(new Achievement(
                    "Week Warrior", "7-day attendance streak",
                    AchievementType.STREAK, 7, 50
            ));
            achievementRepository.save(new Achievement(
                    "Monthly Champion", "30-day attendance streak",
                    AchievementType.STREAK, 30, 200
            ));
            achievementRepository.save(new Achievement(
                    "Century Club", "100 total workouts",
                    AchievementType.WORKOUT, 100, 150
            ));
        }
    }
}