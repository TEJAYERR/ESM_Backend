package com.events_and_stalls_management_system.esm.dto;

import com.events_and_stalls_management_system.esm.entity.Booking;
import com.events_and_stalls_management_system.esm.entity.BookingStatus;
import com.events_and_stalls_management_system.esm.entity.Stall;
import com.events_and_stalls_management_system.esm.entity.StallStatus;
import com.events_and_stalls_management_system.esm.entity.StallType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class StallResponse {

    private UUID id;
    private StallType stallType;
    private StallStatus stallStatus;
    private String stallNumber;
    private BigDecimal price;

    // Booking details — populated only when stall is BOOKED
    private String vendorName;
    private String mobileNumber;
    private String category;
    private String description;
    private String bookingReference;

    public StallResponse(Stall stall) {
        this.id = stall.getId();
        this.stallNumber = stall.getStallNumber();
        this.stallType = stall.getStallType();
        this.stallStatus = stall.getStallStatus();
        this.price = stall.getPrice();

        // Find the confirmed booking for this stall if it exists
        if (stall.getBooking() != null) {
            stall.getBooking().stream()
                    .filter(b -> b.getBookingStatus() == BookingStatus.CONFIRMED)
                    .findFirst()
                    .ifPresent(b -> {
                        this.vendorName = b.getName();
                        this.mobileNumber = b.getMobileNumber();
                        this.category = b.getCategory();
                        this.description = b.getDescription();
                        this.bookingReference = b.getBookingReference();
                    });
        }
    }
}