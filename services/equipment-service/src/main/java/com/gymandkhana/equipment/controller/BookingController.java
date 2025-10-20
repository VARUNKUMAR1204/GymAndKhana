package com.gymandkhana.equipment.controller;

import com.gymandkhana.equipment.dto.BookingRequest;
import com.gymandkhana.equipment.model.EquipmentBooking;
import com.gymandkhana.equipment.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "*")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping
    public ResponseEntity<?> createBooking(@Valid @RequestBody BookingRequest request) {
        try {
            EquipmentBooking booking = bookingService.createBooking(request);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Booking created successfully");
            response.put("booking", booking);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @PutMapping("/{id}/start")
    public ResponseEntity<?> startBooking(@PathVariable String id) {
        try {
            EquipmentBooking booking = bookingService.startBooking(id);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Booking started successfully");
            response.put("booking", booking);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @PutMapping("/{id}/end")
    public ResponseEntity<?> endBooking(@PathVariable String id) {
        try {
            EquipmentBooking booking = bookingService.endBooking(id);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Booking ended successfully");
            response.put("booking", booking);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<?> cancelBooking(@PathVariable String id) {
        try {
            EquipmentBooking booking = bookingService.cancelBooking(id);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Booking cancelled successfully");
            response.put("booking", booking);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserBookings(@PathVariable String userId) {
        try {
            List<EquipmentBooking> bookings = bookingService.getUserBookings(userId);

            Map<String, Object> response = new HashMap<>();
            response.put("userId", userId);
            response.put("total", bookings.size());
            response.put("bookings", bookings);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping("/equipment/{equipmentId}")
    public ResponseEntity<?> getEquipmentBookings(@PathVariable String equipmentId) {
        try {
            List<EquipmentBooking> bookings = bookingService.getEquipmentBookings(equipmentId);

            Map<String, Object> response = new HashMap<>();
            response.put("equipmentId", equipmentId);
            response.put("total", bookings.size());
            response.put("bookings", bookings);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping("/today")
    public ResponseEntity<?> getTodayBookings() {
        try {
            List<EquipmentBooking> bookings = bookingService.getTodayBookings();

            Map<String, Object> response = new HashMap<>();
            response.put("total", bookings.size());
            response.put("bookings", bookings);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
}
