package com.gymandkhana.attendance.controller;

import com.gymandkhana.attendance.dto.*;
import com.gymandkhana.attendance.model.Attendance;
import com.gymandkhana.attendance.service.AttendanceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/attendance")
@CrossOrigin(origins = "*")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @PostMapping("/check-in")
    public ResponseEntity<?> checkIn(@Valid @RequestBody CheckInRequest request) {
        try {
            CheckInResponse response = attendanceService.checkIn(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    @PostMapping("/check-out/{userId}")
    public ResponseEntity<?> checkOut(@PathVariable String userId) {
        try {
            CheckInResponse response = attendanceService.checkOut(userId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    @GetMapping("/user/{userId}/history")
    public ResponseEntity<?> getUserAttendanceHistory(@PathVariable String userId) {
        try {
            List<Attendance> attendances = attendanceService.getUserAttendanceHistory(userId);

            Map<String, Object> response = new HashMap<>();
            response.put("userId", userId);
            response.put("totalRecords", attendances.size());
            response.put("attendances", attendances);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping("/user/{userId}/history/range")
    public ResponseEntity<?> getUserAttendanceByDateRange(
            @PathVariable String userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        try {
            List<Attendance> attendances = attendanceService.getUserAttendanceByDateRange(
                    userId, startDate, endDate);

            Map<String, Object> response = new HashMap<>();
            response.put("userId", userId);
            response.put("startDate", startDate);
            response.put("endDate", endDate);
            response.put("totalRecords", attendances.size());
            response.put("attendances", attendances);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping("/user/{userId}/stats")
    public ResponseEntity<?> getUserAttendanceStats(@PathVariable String userId) {
        try {
            AttendanceStatsResponse stats = attendanceService.getUserAttendanceStats(userId);

            Map<String, Object> response = new HashMap<>();
            response.put("userId", userId);
            response.put("stats", stats);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping("/user/{userId}/status")
    public ResponseEntity<?> getUserCheckInStatus(@PathVariable String userId) {
        try {
            boolean isCheckedIn = attendanceService.isUserCheckedInToday(userId);

            Map<String, Object> response = new HashMap<>();
            response.put("userId", userId);
            response.put("isCheckedIn", isCheckedIn);
            response.put("date", LocalDate.now());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping("/today/active-count")
    public ResponseEntity<?> getTodayActiveCount() {
        try {
            long activeCount = attendanceService.getTodayActiveCount();

            Map<String, Object> response = new HashMap<>();
            response.put("date", LocalDate.now());
            response.put("activeCount", activeCount);
            response.put("message", activeCount + " members currently active");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping("/today/all")
    public ResponseEntity<?> getTodayAllAttendances() {
        try {
            List<Attendance> attendances = attendanceService.getTodayAllAttendances();

            Map<String, Object> response = new HashMap<>();
            response.put("date", LocalDate.now());
            response.put("totalAttendances", attendances.size());
            response.put("attendances", attendances);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
}
