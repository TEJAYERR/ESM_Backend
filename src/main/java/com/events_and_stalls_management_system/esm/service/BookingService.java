package com.events_and_stalls_management_system.esm.service;

import com.events_and_stalls_management_system.esm.dto.BookingResponse;
import com.events_and_stalls_management_system.esm.dto.BookingUpdateRequest;
import com.events_and_stalls_management_system.esm.entity.Booking;
import com.events_and_stalls_management_system.esm.entity.BookingStatus;
import com.events_and_stalls_management_system.esm.exception.BadRequestException;
import com.events_and_stalls_management_system.esm.exception.ResourceNotFoundException;
import com.events_and_stalls_management_system.esm.exception.UnauthorizedActionException;
import com.events_and_stalls_management_system.esm.repository.BookingRepo;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BookingService {


    private final BookingRepo bookingRepo;

    public BookingService(BookingRepo bookingRepo) {
        this.bookingRepo = bookingRepo;
    }

    public List<BookingResponse> getAllBookings(
            BookingStatus bookingStatus,
            String searchKeyword) {

        List<Booking> bookings;

        if (searchKeyword == null || searchKeyword.isBlank()) {
            bookings = bookingRepo.findAllByBookingStatus(bookingStatus);
        } else {
            bookings = bookingRepo.searchBookings(
                    bookingStatus,
                    searchKeyword.trim()
            );
        }

        return bookings.stream()
                .map(BookingResponse::new)
                .toList();
    }

    @Transactional
    public void updateBookingVendorDetails(
            UUID bookingId,
            BookingUpdateRequest request) {

        Booking booking = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        if (booking.getBookingStatus() != BookingStatus.CONFIRMED) {
            throw new BadRequestException(
                    "Vendor details can only be edited for confirmed bookings");
        }

        if(!request.getName().isEmpty()){
            System.out.println("Name = " + request.getName());
            booking.setName(request.getName());
        }
        if(!request.getMobileNumber().isEmpty()){
            booking.setMobileNumber(request.getMobileNumber());
        }
        if(!request.getCategory().isEmpty()){
            booking.setCategory(request.getCategory());
        }
        if(!request.getDescription().isEmpty()){
            booking.setDescription(request.getDescription());
        }

        bookingRepo.save(booking);
    }
}
