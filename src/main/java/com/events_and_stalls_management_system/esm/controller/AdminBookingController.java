package com.events_and_stalls_management_system.esm.controller;

import com.events_and_stalls_management_system.esm.dto.BookingUpdateRequest;
import com.events_and_stalls_management_system.esm.entity.BookingStatus;
import com.events_and_stalls_management_system.esm.service.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class AdminBookingController {

    private final BookingService bookingService;

    public AdminBookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/bookings")
    public ResponseEntity<?> getAllBookings(@RequestParam BookingStatus bookingStatus,
                                            @RequestParam(required = false) String searchKeyword){

        return new ResponseEntity<>(bookingService.getAllBookings(bookingStatus, searchKeyword), HttpStatus.OK);
    }

    @PatchMapping("/admin/booking/{bookingId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateBookingVendorDetails(@PathVariable UUID bookingId,
                                                 @RequestBody BookingUpdateRequest request) {

        bookingService.updateBookingVendorDetails(bookingId, request);

        return ResponseEntity.ok("Vendor details updated successfully");
    }
}
