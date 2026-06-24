package com.events_and_stalls_management_system.esm.controller;

import com.events_and_stalls_management_system.esm.dto.StallRequest;
import com.events_and_stalls_management_system.esm.service.StallService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/admin/events/{eventId}/stalls")
public class AdminStallController {

    private final StallService stallService;

    public AdminStallController(StallService stallService) {
        this.stallService = stallService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addStall(@PathVariable UUID eventId, @RequestBody StallRequest stallRequest){
        return new ResponseEntity<>(stallService.addStall(eventId, stallRequest), HttpStatus.OK);
    }

    @PatchMapping("/{stallId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateStall(@PathVariable UUID eventId, @PathVariable UUID stallId, @RequestBody StallRequest stallRequest){
        return new ResponseEntity<>(stallService.updateStall(eventId, stallId, stallRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{stallId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteStall(@PathVariable UUID eventId, @PathVariable UUID stallId){
        return new ResponseEntity<>(stallService.deleteStall(eventId, stallId), HttpStatus.OK);
    }
}
