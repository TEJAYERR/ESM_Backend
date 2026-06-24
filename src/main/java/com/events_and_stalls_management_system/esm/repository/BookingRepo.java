package com.events_and_stalls_management_system.esm.repository;

import com.events_and_stalls_management_system.esm.entity.Booking;
import com.events_and_stalls_management_system.esm.entity.BookingStatus;
import com.events_and_stalls_management_system.esm.entity.Stall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookingRepo extends JpaRepository<Booking, UUID> {
    Booking getBookingByStallAndRazorpayOrderId(Stall stall, String razorpayOrderId);

    Optional<Booking> findByStallAndRazorpayOrderId(Stall stall, String razorpayOrderId);

    List<Booking> findAllByBookingStatus(BookingStatus bookingStatus);

    @Query("""
        SELECT b
        FROM bookings b
        WHERE b.bookingStatus = :status
        AND (
            LOWER(b.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
            OR LOWER(b.bookingReference) LIKE LOWER(CONCAT('%', :keyword, '%'))
            OR LOWER(b.stall.stallNumber) LIKE LOWER(CONCAT('%', :keyword, '%'))
        )
    """)
    List<Booking> searchBookings(
            @Param("status") BookingStatus status,
            @Param("keyword") String keyword
    );

    Collection<Object> findByBookingStatusAndStall(BookingStatus bookingStatus, Stall stall);
}
