package com.gymandkhana.attendance.repository;

import com.gymandkhana.attendance.model.Attendance;
import com.gymandkhana.attendance.model.AttendanceStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends MongoRepository<Attendance, String> {

    Optional<Attendance> findByUserIdAndDate(String userId, LocalDate date);

    List<Attendance> findByUserIdOrderByDateDesc(String userId);

    List<Attendance> findByUserIdAndDateBetweenOrderByDateDesc(String userId, LocalDate startDate, LocalDate endDate);

    @Query("{ 'userId': ?0, 'status': ?1 }")
    List<Attendance> findByUserIdAndStatus(String userId, AttendanceStatus status);

    long countByUserIdAndStatus(String userId, AttendanceStatus status);

    @Query("{ 'date': ?0 }")
    List<Attendance> findAllByDate(LocalDate date);

    @Query("{ 'date': ?0, 'status': 'CHECKED_IN' }")
    long countActiveAttendancesByDate(LocalDate date);
}