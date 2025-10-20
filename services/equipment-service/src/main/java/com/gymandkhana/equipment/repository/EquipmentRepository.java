package com.gymandkhana.equipment.repository;

import com.gymandkhana.equipment.model.Equipment;
import com.gymandkhana.equipment.model.EquipmentStatus;
import com.gymandkhana.equipment.model.EquipmentType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EquipmentRepository extends MongoRepository<Equipment, String> {

    Optional<Equipment> findByEquipmentCode(String equipmentCode);

    List<Equipment> findByStatus(EquipmentStatus status);

    List<Equipment> findByType(EquipmentType type);

    List<Equipment> findByLocation(String location);

    @Query("{ 'status': ?0, 'type': ?1 }")
    List<Equipment> findByStatusAndType(EquipmentStatus status, EquipmentType type);

    long countByStatus(EquipmentStatus status);

    long countByType(EquipmentType type);

    boolean existsByEquipmentCode(String equipmentCode);
}