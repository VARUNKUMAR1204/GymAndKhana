package com.gymandkhana.equipment.repository;

import com.gymandkhana.equipment.model.EquipmentBooking;
import com.gymandkhana.equipment.model.BookingStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface EquipmentBookingRepository extends MongoRepository<EquipmentBooking, String> {

    List<EquipmentBooking> findByUserId(String userId);

    List<EquipmentBooking> findByEquipmentId(String equipmentId);

    List<EquipmentBooking> findByStatus(BookingStatus status);

    @Query("{ 'equipmentId': ?0, 'status': ?1 }")
    Optional<EquipmentBooking> findActiveBookingByEquipment(String equipmentId, BookingStatus status);

    @Query("{ 'userId': ?0, 'status': ?1 }")
    List<EquipmentBooking> findByUserIdAndStatus(String userId, BookingStatus status);

    @Query("{ 'createdAt': { $gte: ?0, $lte: ?1 } }")
    List<EquipmentBooking> findBookingsByDateRange(LocalDateTime start, LocalDateTime end);
}