package com.events_and_stalls_management_system.esm.controller;

import com.events_and_stalls_management_system.esm.dto.EventRequest;
import com.events_and_stalls_management_system.esm.dto.EventUpdateRequest;
import com.events_and_stalls_management_system.esm.service.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/admin")
public class AdminEventController {


    private final EventService eventService;

    public AdminEventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/events")
    public ResponseEntity<?> getEvents(){
        return new ResponseEntity<>(eventService.getEvents(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/events/{eventId}")
    public ResponseEntity<?> getEventById(@PathVariable UUID eventId){
        return new ResponseEntity<>(eventService.getEventById(eventId), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/events")
    public ResponseEntity<?> createEvent(@RequestPart EventRequest eventRequest, @RequestPart MultipartFile blueprintFile) throws IOException {
        return new ResponseEntity<>(eventService.createEvent(eventRequest, blueprintFile), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/events/{eventId}")
    public ResponseEntity<?> deleteEvent(@PathVariable UUID eventId){
        return new ResponseEntity<>(eventService.deleteEvent(eventId), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/events/{eventId}")
    public ResponseEntity<?> updateEvent(@PathVariable UUID eventId, @RequestBody EventUpdateRequest eventUpdateRequest){
        return new ResponseEntity<>(eventService.updateEvent(eventId, eventUpdateRequest), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/events/{eventId}/blueprint")
    public ResponseEntity<?> updateEventStallBluePrint(@PathVariable UUID eventId, @RequestPart MultipartFile blueprintFile) throws IOException {
        return new ResponseEntity<>(eventService.updateEventStallBluePrint(eventId, blueprintFile), HttpStatus.OK);
    }
}
