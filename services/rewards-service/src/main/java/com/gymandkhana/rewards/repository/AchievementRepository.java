package com.gymandkhana.rewards.repository;

import com.gymandkhana.rewards.model.Achievement;
import com.gymandkhana.rewards.model.AchievementType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AchievementRepository extends MongoRepository<Achievement, String> {
    List<Achievement> findByType(AchievementType type);
}