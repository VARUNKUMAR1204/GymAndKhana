package com.gymandkhana.user.controller;

import com.gymandkhana.user.dto.UserResponse;
import com.gymandkhana.user.model.MembershipType;
import com.gymandkhana.user.model.User;
import com.gymandkhana.user.model.UserStatus;
import com.gymandkhana.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/profile/{id}")
    public ResponseEntity<?> getUserProfile(@PathVariable String id) {
        try {
            User user = userService.findById(id);
            UserResponse userResponse = userService.convertToResponse(user);
            return ResponseEntity.ok(userResponse);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @PutMapping("/profile/{id}")
    public ResponseEntity<?> updateUserProfile(@PathVariable String id, @RequestBody User updatedUser) {
        try {
            User user = userService.updateUser(id, updatedUser);
            UserResponse userResponse = userService.convertToResponse(user);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Profile updated successfully");
            response.put("user", userResponse);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @PutMapping("/{id}/membership")
    public ResponseEntity<?> updateMembership(@PathVariable String id,
                                              @RequestParam MembershipType membershipType) {
        try {
            User user = userService.updateMembership(id, membershipType);
            UserResponse userResponse = userService.convertToResponse(user);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Membership updated successfully");
            response.put("user", userResponse);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllUsers() {
        try {
            List<User> users = userService.getAllUsers();
            List<UserResponse> userResponses = users.stream()
                    .map(userService::convertToResponse)
                    .collect(Collectors.toList());

            Map<String, Object> response = new HashMap<>();
            response.put("users", userResponses);
            response.put("total", userResponses.size());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping("/stats")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getUserStats() {
        try {
            long totalActiveUsers = userService.getTotalActiveUsers();
            List<User> expiredUsers = userService.getUsersWithExpiredMemberships();

            Map<String, Object> stats = new HashMap<>();
            stats.put("totalActiveUsers", totalActiveUsers);
            stats.put("expiredMemberships", expiredUsers.size());
            stats.put("totalUsers", userService.getAllUsers().size());

            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateUserStatus(@PathVariable String id,
                                              @RequestParam UserStatus status) {
        try {
            User user = userService.updateUserStatus(id, status);
            UserResponse userResponse = userService.convertToResponse(user);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "User status updated successfully");
            response.put("user", userResponse);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable String id) {
        try {
            userService.deleteUser(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "User deleted successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
}