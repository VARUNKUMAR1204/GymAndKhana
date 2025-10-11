package com.gymandkhana.rewards.repository;

import com.gymandkhana.rewards.model.UserPoints;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserPointsRepository extends MongoRepository<UserPoints, String> {
    Optional<UserPoints> findByUserId(String userId);
    boolean existsByUserId(String userId);

    @Query("{ 'totalPoints': { $gte: ?0 } }")
    List<UserPoints> findUsersWithMinimumPoints(int minPoints);

    List<UserPoints> findAllByOrderByTotalPointsDesc();
}