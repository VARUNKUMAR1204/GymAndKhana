package com.gymandkhana.equipment.service;

import com.gymandkhana.equipment.dto.EquipmentRequest;
import com.gymandkhana.equipment.dto.EquipmentStatsResponse;
import com.gymandkhana.equipment.model.*;
import com.gymandkhana.equipment.repository.EquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EquipmentService {

    @Autowired
    private EquipmentRepository equipmentRepository;

    public Equipment addEquipment(EquipmentRequest request) {
        if (equipmentRepository.existsByEquipmentCode(request.getEquipmentCode())) {
            throw new RuntimeException("Equipment with this code already exists");
        }

        Equipment equipment = new Equipment();
        equipment.setEquipmentCode(request.getEquipmentCode());
        equipment.setName(request.getName());
        equipment.setDescription(request.getDescription());
        equipment.setType(request.getType());
        equipment.setLocation(request.getLocation());
        equipment.setImageUrl(request.getImageUrl());

        return equipmentRepository.save(equipment);
    }

    public Equipment updateEquipment(String id, EquipmentRequest request) {
        Equipment equipment = findById(id);

        equipment.setName(request.getName());
        equipment.setDescription(request.getDescription());
        equipment.setType(request.getType());
        equipment.setLocation(request.getLocation());
        equipment.setImageUrl(request.getImageUrl());

        return equipmentRepository.save(equipment);
    }

    public Equipment findById(String id) {
        return equipmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipment not found with id: " + id));
    }

    public Equipment findByCode(String code) {
        return equipmentRepository.findByEquipmentCode(code)
                .orElseThrow(() -> new RuntimeException("Equipment not found with code: " + code));
    }

    public List<Equipment> getAllEquipments() {
        return equipmentRepository.findAll();
    }

    public List<Equipment> getEquipmentsByStatus(EquipmentStatus status) {
        return equipmentRepository.findByStatus(status);
    }

    public List<Equipment> getEquipmentsByType(EquipmentType type) {
        return equipmentRepository.findByType(type);
    }

    public Equipment markInUse(String equipmentId, String userId) {
        Equipment equipment = findById(equipmentId);

        if (!equipment.isAvailable()) {
            throw new RuntimeException("Equipment is not available");
        }

        equipment.markInUse(userId);
        return equipmentRepository.save(equipment);
    }

    public Equipment markAvailable(String equipmentId) {
        Equipment equipment = findById(equipmentId);
        equipment.markAvailable();
        return equipmentRepository.save(equipment);
    }

    public Equipment markUnderMaintenance(String equipmentId, String notes) {
        Equipment equipment = findById(equipmentId);
        equipment.markUnderMaintenance();

        if (equipment.getMaintenanceInfo() != null) {
            equipment.getMaintenanceInfo().setMaintenanceNotes(notes);
        }

        return equipmentRepository.save(equipment);
    }

    public EquipmentStatsResponse getEquipmentStats() {
        List<Equipment> allEquipments = equipmentRepository.findAll();

        EquipmentStatsResponse stats = new EquipmentStatsResponse();
        stats.setTotalEquipments(allEquipments.size());
        stats.setAvailableCount((int) equipmentRepository.countByStatus(EquipmentStatus.AVAILABLE));
        stats.setInUseCount((int) equipmentRepository.countByStatus(EquipmentStatus.IN_USE));
        stats.setUnderMaintenanceCount((int) equipmentRepository.countByStatus(EquipmentStatus.UNDER_MAINTENANCE));
        stats.setOutOfOrderCount((int) equipmentRepository.countByStatus(EquipmentStatus.OUT_OF_ORDER));

        // Equipment by type
        Map<String, Integer> byType = allEquipments.stream()
                .collect(Collectors.groupingBy(
                        e -> e.getType().toString(),
                        Collectors.collectingAndThen(Collectors.counting(), Long::intValue)
                ));
        stats.setEquipmentByType(byType);

        // Usage by location
        Map<String, Integer> byLocation = allEquipments.stream()
                .filter(e -> e.getLocation() != null)
                .collect(Collectors.groupingBy(
                        Equipment::getLocation,
                        Collectors.collectingAndThen(Collectors.counting(), Long::intValue)
                ));
        stats.setUsageByLocation(byLocation);

        return stats;
    }

    public void deleteEquipment(String id) {
        Equipment equipment = findById(id);
        equipmentRepository.delete(equipment);
    }
}
