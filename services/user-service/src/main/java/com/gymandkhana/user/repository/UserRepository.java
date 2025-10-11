package com.gymandkhana.user.repository;

import com.gymandkhana.user.model.User;
import com.gymandkhana.user.model.UserStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User,String> {
    Optional<User> findByEmail(String email);

    Optional<User> findByPhoneNumber(String phoneNumber);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    List<User> findByStatus(UserStatus status);

    @Query("{ 'membership.endDate' : { $lt: ?0 } }")
    List<User> findUsersWithExpiredMemberships(LocalDateTime date);

    @Query("{ 'createdAt' : { $gte: ?0, $lte: ?1 } }")
    List<User> findUsersByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

    long countByStatus(UserStatus status);
}
