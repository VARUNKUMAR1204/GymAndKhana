package com.gymandkhana.equipment.service;

import com.gymandkhana.equipment.dto.BookingRequest;
import com.gymandkhana.equipment.model.BookingStatus;
import com.gymandkhana.equipment.model.Equipment;
import com.gymandkhana.equipment.model.EquipmentBooking;
import com.gymandkhana.equipment.repository.EquipmentBookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingService {

    @Autowired
    private EquipmentBookingRepository bookingRepository;

    @Autowired
    private EquipmentService equipmentService;

    public EquipmentBooking createBooking(BookingRequest request) {
        Equipment equipment = equipmentService.findById(request.getEquipmentId());

        if (!equipment.isAvailable()) {
            throw new RuntimeException("Equipment is not available for booking");
        }

        EquipmentBooking booking = new EquipmentBooking(
                request.getEquipmentId(),
                request.getUserId(),
                request.getStartTime(),
                request.getEndTime()
        );

        return bookingRepository.save(booking);
    }

    public EquipmentBooking startBooking(String bookingId) {
        EquipmentBooking booking = findById(bookingId);

        if (booking.getStatus() != BookingStatus.PENDING) {
            throw new RuntimeException("Booking is not in pending status");
        }

        booking.startUsage();
        equipmentService.markInUse(booking.getEquipmentId(), booking.getUserId());

        return bookingRepository.save(booking);
    }

    public EquipmentBooking endBooking(String bookingId) {
        EquipmentBooking booking = findById(bookingId);

        if (booking.getStatus() != BookingStatus.ACTIVE) {
            throw new RuntimeException("Booking is not active");
        }

        booking.endUsage();
        equipmentService.markAvailable(booking.getEquipmentId());

        return bookingRepository.save(booking);
    }

    public EquipmentBooking cancelBooking(String bookingId) {
        EquipmentBooking booking = findById(bookingId);
        booking.setStatus(BookingStatus.CANCELLED);
        return bookingRepository.save(booking);
    }

    public EquipmentBooking findById(String id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + id));
    }

    public List<EquipmentBooking> getUserBookings(String userId) {
        return bookingRepository.findByUserId(userId);
    }

    public List<EquipmentBooking> getEquipmentBookings(String equipmentId) {
        return bookingRepository.findByEquipmentId(equipmentId);
    }

    public List<EquipmentBooking> getTodayBookings() {
        LocalDateTime startOfDay = LocalDateTime.now().toLocalDate().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1);
        return bookingRepository.findBookingsByDateRange(startOfDay, endOfDay);
    }
}