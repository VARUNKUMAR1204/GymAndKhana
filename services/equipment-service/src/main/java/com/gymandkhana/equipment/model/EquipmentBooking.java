package com.gymandkhana.equipment.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.LocalDateTime;

@Document(collection = "equipment_bookings")
public class EquipmentBooking {
    @Id
    private String id;

    @Indexed
    private String equipmentId;

    @Indexed
    private String userId;

    private LocalDateTime bookingStartTime;
    private LocalDateTime bookingEndTime;
    private LocalDateTime actualStartTime;
    private LocalDateTime actualEndTime;

    private BookingStatus status;

    @CreatedDate
    private LocalDateTime createdAt;

    // Constructors
    public EquipmentBooking() {
        this.status = BookingStatus.PENDING;
    }

    public EquipmentBooking(String equipmentId, String userId,
                            LocalDateTime startTime, LocalDateTime endTime) {
        this();
        this.equipmentId = equipmentId;
        this.userId = userId;
        this.bookingStartTime = startTime;
        this.bookingEndTime = endTime;
    }

    public void startUsage() {
        this.actualStartTime = LocalDateTime.now();
        this.status = BookingStatus.ACTIVE;
    }

    public void endUsage() {
        this.actualEndTime = LocalDateTime.now();
        this.status = BookingStatus.COMPLETED;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getEquipmentId() { return equipmentId; }
    public void setEquipmentId(String equipmentId) { this.equipmentId = equipmentId; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public LocalDateTime getBookingStartTime() { return bookingStartTime; }
    public void setBookingStartTime(LocalDateTime bookingStartTime) {
        this.bookingStartTime = bookingStartTime;
    }

    public LocalDateTime getBookingEndTime() { return bookingEndTime; }
    public void setBookingEndTime(LocalDateTime bookingEndTime) {
        this.bookingEndTime = bookingEndTime;
    }

    public LocalDateTime getActualStartTime() { return actualStartTime; }
    public void setActualStartTime(LocalDateTime actualStartTime) {
        this.actualStartTime = actualStartTime;
    }

    public LocalDateTime getActualEndTime() { return actualEndTime; }
    public void setActualEndTime(LocalDateTime actualEndTime) {
        this.actualEndTime = actualEndTime;
    }

    public BookingStatus getStatus() { return status; }
    public void setStatus(BookingStatus status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}