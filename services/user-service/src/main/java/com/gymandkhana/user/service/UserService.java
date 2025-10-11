package com.gymandkhana.user.service;

import com.gymandkhana.user.exception.UserNotFoundException;
import com.gymandkhana.user.dto.RegisterRequest;
import com.gymandkhana.user.dto.UserResponse;
import com.gymandkhana.user.model.Membership;
import com.gymandkhana.user.model.User;
import com.gymandkhana.user.model.UserRole;
import com.gymandkhana.user.model.UserStatus;
import com.gymandkhana.user.model.*;
import com.gymandkhana.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(RegisterRequest request) {
        // Check if user already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("User with this email already exists");
        }

        if (userRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new RuntimeException("User with this phone number already exists");
        }

        // Create new user
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(UserRole.MEMBER);
        user.setStatus(UserStatus.ACTIVE);

        // Create membership
        Membership membership = createMembership(request.getMembershipType());
        user.setMembership(membership);

        return userRepository.save(user);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
    }

    public User updateUser(String id, User updatedUser) {
        User existingUser = findById(id);

        existingUser.setFirstName(updatedUser.getFirstName());
        existingUser.setLastName(updatedUser.getLastName());
        existingUser.setPhoneNumber(updatedUser.getPhoneNumber());
        existingUser.setProfileImage(updatedUser.getProfileImage());

        return userRepository.save(existingUser);
    }

    public User updateMembership(String userId, MembershipType membershipType) {
        User user = findById(userId);
        Membership newMembership = createMembership(membershipType);
        user.setMembership(newMembership);
        return userRepository.save(user);
    }

    public User updateUserStatus(String userId, UserStatus status) {
        User user = findById(userId);
        user.setStatus(status);
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<User> getUsersByStatus(UserStatus status) {
        return userRepository.findByStatus(status);
    }

    public List<User> getUsersWithExpiredMemberships() {
        return userRepository.findUsersWithExpiredMemberships(LocalDateTime.now());
    }

    public void deleteUser(String id) {
        User user = findById(id);
        userRepository.delete(user);
    }

    public UserResponse convertToResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());
        response.setPhoneNumber(user.getPhoneNumber());
        response.setProfileImage(user.getProfileImage());
        response.setRole(user.getRole());
        response.setStatus(user.getStatus());
        response.setMembership(user.getMembership());
        response.setCreatedAt(user.getCreatedAt());
        return response;
    }

    private Membership createMembership(MembershipType type) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime endDate = now.plusMonths(1); // 1 month membership

        return new Membership(type, now, endDate, type.getDefaultPrice());
    }

    public long getTotalActiveUsers() {
        return userRepository.countByStatus(UserStatus.ACTIVE);
    }

    public boolean isEmailAvailable(String email) {
        return !userRepository.existsByEmail(email);
    }

    public boolean isPhoneNumberAvailable(String phoneNumber) {
        return !userRepository.existsByPhoneNumber(phoneNumber);
    }
}
