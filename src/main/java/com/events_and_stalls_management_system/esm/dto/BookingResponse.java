package com.events_and_stalls_management_system.esm.dto;

import com.events_and_stalls_management_system.esm.entity.Booking;
import com.events_and_stalls_management_system.esm.entity.BookingStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class BookingResponse {

    private UUID bookingId;
    private String bookingReference;
    private String vendorName;
    private String mobileNumber;
    private String description;
    private String category;
    private BigDecimal amount;
    private BookingStatus bookingStatus;
    private LocalDateTime createdAt;

    // For Razorpay checkout (used after createBooking)
    private String razorpayOrderId;
    private String razorpayKey;

    // For booking listing table (admin)
    private String eventName;
    private String stallNumber;

    public BookingResponse(Booking booking) {
        this.bookingId = booking.getBookingId();
        this.bookingReference = booking.getBookingReference();
        this.vendorName = booking.getName();
        this.mobileNumber = booking.getMobileNumber();
        this.description = booking.getDescription();
        this.category = booking.getCategory();
        this.amount = booking.getAmount();
        this.bookingStatus = booking.getBookingStatus();
        this.createdAt = booking.getCreatedAt();
        this.razorpayOrderId = booking.getRazorpayOrderId();
        this.razorpayKey = "rzp_test_SfGIn89zRbphHF";

        // Navigate Booking → Stall → Event
        if (booking.getStall() != null) {
            this.stallNumber = booking.getStall().getStallNumber();
            if (booking.getStall().getEvent() != null) {
                this.eventName = booking.getStall().getEvent().getName();
            }
        }
    }
}