package com.events_and_stalls_management_system.esm.controller;

import com.events_and_stalls_management_system.esm.dto.BookingRequest;
import com.events_and_stalls_management_system.esm.dto.PaymentVerificationRequest;
import com.events_and_stalls_management_system.esm.entity.Event;
import com.events_and_stalls_management_system.esm.service.EventService;
import com.events_and_stalls_management_system.esm.service.StallService;
import com.razorpay.RazorpayException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/events/{eventId}")
public class PublicEventController {

    private final EventService eventService;
    private final StallService stallService;

    public PublicEventController(EventService eventService, StallService stallService) {
        this.eventService = eventService;
        this.stallService = stallService;
    }

    @GetMapping
    public ResponseEntity<?> getEventById(@PathVariable UUID eventId){
        return new ResponseEntity<>(eventService.getEventById(eventId), HttpStatus.OK);
    }

    @GetMapping("/blueprint")
    public ResponseEntity<byte[]> getEventBlueprintById(@PathVariable UUID eventId){

        Event event = eventService.getEvent(eventId);

        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(event.getStallsBluePrintContentType()))
                .body(event.getStallsBluePrint());
    }

    @GetMapping("/stalls/{stallId}")
    public ResponseEntity<?> getStallById(@PathVariable UUID eventId, @PathVariable UUID stallId){

        return new ResponseEntity<>(stallService.getStallById(eventId, stallId), HttpStatus.OK);
    }

    @PostMapping("/stalls/{stallId}/book")
    public ResponseEntity<?> bookStall(@PathVariable UUID eventId, @PathVariable UUID stallId, @RequestBody BookingRequest stallBookingRequest) throws RazorpayException {

        return new ResponseEntity<>(stallService.createBooking(eventId, stallId, stallBookingRequest), HttpStatus.OK);
    }

    @PostMapping("/stalls/{stallId}/book/verify-payment")
    public ResponseEntity<?> verifyPayment(@PathVariable UUID eventId, @PathVariable UUID stallId, @RequestBody PaymentVerificationRequest paymentVerificationRequest) throws Exception {

        return new ResponseEntity<>(stallService.verifyPayment(eventId, stallId, paymentVerificationRequest), HttpStatus.OK);
    }
}
