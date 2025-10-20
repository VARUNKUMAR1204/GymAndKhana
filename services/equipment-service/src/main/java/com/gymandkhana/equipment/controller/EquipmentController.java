package com.gymandkhana.equipment.controller;

import com.gymandkhana.equipment.dto.EquipmentRequest;
import com.gymandkhana.equipment.dto.EquipmentStatsResponse;
import com.gymandkhana.equipment.model.Equipment;
import com.gymandkhana.equipment.model.EquipmentStatus;
import com.gymandkhana.equipment.model.EquipmentType;
import com.gymandkhana.equipment.service.EquipmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/equipment")
@CrossOrigin(origins = "*")
public class EquipmentController {

    @Autowired
    private EquipmentService equipmentService;

    @PostMapping
    public ResponseEntity<?> addEquipment(@Valid @RequestBody EquipmentRequest request) {
        try {
            Equipment equipment = equipmentService.addEquipment(request);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Equipment added successfully");
            response.put("equipment", equipment);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEquipment(@PathVariable String id,
                                             @Valid @RequestBody EquipmentRequest request) {
        try {
            Equipment equipment = equipmentService.updateEquipment(id, request);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Equipment updated successfully");
            response.put("equipment", equipment);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEquipmentById(@PathVariable String id) {
        try {
            Equipment equipment = equipmentService.findById(id);
            return ResponseEntity.ok(equipment);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<?> getEquipmentByCode(@PathVariable String code) {
        try {
            Equipment equipment = equipmentService.findByCode(code);
            return ResponseEntity.ok(equipment);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllEquipments() {
        try {
            List<Equipment> equipments = equipmentService.getAllEquipments();

            Map<String, Object> response = new HashMap<>();
            response.put("total", equipments.size());
            response.put("equipments", equipments);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<?> getEquipmentsByStatus(@PathVariable EquipmentStatus status) {
        try {
            List<Equipment> equipments = equipmentService.getEquipmentsByStatus(status);

            Map<String, Object> response = new HashMap<>();
            response.put("status", status);
            response.put("count", equipments.size());
            response.put("equipments", equipments);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<?> getEquipmentsByType(@PathVariable EquipmentType type) {
        try {
            List<Equipment> equipments = equipmentService.getEquipmentsByType(type);

            Map<String, Object> response = new HashMap<>();
            response.put("type", type);
            response.put("count", equipments.size());
            response.put("equipments", equipments);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @PutMapping("/{id}/mark-in-use")
    public ResponseEntity<?> markInUse(@PathVariable String id, @RequestParam String userId) {
        try {
            Equipment equipment = equipmentService.markInUse(id, userId);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Equipment marked as in use");
            response.put("equipment", equipment);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @PutMapping("/{id}/mark-available")
    public ResponseEntity<?> markAvailable(@PathVariable String id) {
        try {
            Equipment equipment = equipmentService.markAvailable(id);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Equipment marked as available");
            response.put("equipment", equipment);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @PutMapping("/{id}/maintenance")
    public ResponseEntity<?> markUnderMaintenance(@PathVariable String id,
                                                  @RequestParam(required = false) String notes) {
        try {
            Equipment equipment = equipmentService.markUnderMaintenance(id, notes);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Equipment marked under maintenance");
            response.put("equipment", equipment);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping("/stats")
    public ResponseEntity<?> getEquipmentStats() {
        try {
            EquipmentStatsResponse stats = equipmentService.getEquipmentStats();
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEquipment(@PathVariable String id) {
        try {
            equipmentService.deleteEquipment(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Equipment deleted successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
}