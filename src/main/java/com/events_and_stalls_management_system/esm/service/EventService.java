package com.events_and_stalls_management_system.esm.service;

import com.events_and_stalls_management_system.esm.dto.EventRequest;
import com.events_and_stalls_management_system.esm.dto.EventResponse;
import com.events_and_stalls_management_system.esm.dto.EventUpdateRequest;
import com.events_and_stalls_management_system.esm.dto.StallRequest;
import com.events_and_stalls_management_system.esm.entity.Event;
import com.events_and_stalls_management_system.esm.entity.Stall;
import com.events_and_stalls_management_system.esm.exception.BadRequestException;
import com.events_and_stalls_management_system.esm.exception.ResourceNotFoundException;
import com.events_and_stalls_management_system.esm.repository.EventRepo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class EventService {

    private final EventRepo eventRepo;

    public EventService(EventRepo eventRepo) {
        this.eventRepo = eventRepo;
    }

    public EventResponse createEvent(EventRequest eventRequest, MultipartFile multipartFile) throws IOException {

        if (multipartFile.isEmpty()) {
            throw new BadRequestException("layout is not given or provided!");
        }

        if(eventRequest.getStartDate().isAfter(eventRequest.getEndDate())){
            throw new BadRequestException("Invalid dates given");
        }

        Event event = new Event();
        event.setName(eventRequest.getName());
        event.setVenue(eventRequest.getVenue());
        event.setDescription(eventRequest.getDescription());
        event.setStartDate(eventRequest.getStartDate());
        event.setEndDate(eventRequest.getEndDate());
        event.setStallsBluePrint(multipartFile.getBytes());
        event.setStallsBluePrintContentType(multipartFile.getContentType());

        List<Stall> stalls = new ArrayList<>();

        if (eventRequest.getStallRequests() != null) {
            for (StallRequest stallRequest : eventRequest.getStallRequests()) {
                Stall stall = new Stall();
                stall.setEvent(event);
                stall.setStallNumber(stallRequest.getStallNumber());
                stall.setPrice(stallRequest.getPrice());
                stall.setStallType(stallRequest.getStallType());
                stalls.add(stall);
            }
        }

        event.setStalls(stalls);
        eventRepo.save(event);
        return new EventResponse(event);
    }

    public List<EventResponse> getEvents() {

        return eventRepo.findAll()
                .stream()
                .map(EventResponse::new)
                .toList();
    }

    public EventResponse deleteEvent(UUID eventId) {

        Event event = eventRepo.findById(eventId).orElseThrow();
        eventRepo.deleteById(eventId);
        return new EventResponse(event);
    }

    public EventResponse updateEvent(UUID eventId, EventUpdateRequest eventUpdateRequest) {

        Event event = eventRepo.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("event not found"));

        if (eventUpdateRequest.getName() != null) {
            event.setName(eventUpdateRequest.getName());
        }
        if (eventUpdateRequest.getVenue() != null) {
            event.setVenue(eventUpdateRequest.getVenue());
        }
        if (eventUpdateRequest.getDescription() != null) {
            event.setDescription(eventUpdateRequest.getDescription());
        }
        if (eventUpdateRequest.getStartDate() != null) {
            assert eventUpdateRequest.getEndDate() != null;
            if(eventUpdateRequest.getStartDate().isAfter(eventUpdateRequest.getEndDate())){
                throw new BadRequestException("Invalid dates given");
            }
            event.setStartDate(eventUpdateRequest.getStartDate());
        }
        if (eventUpdateRequest.getEndDate() != null) {
            assert eventUpdateRequest.getStartDate() != null;
            if(eventUpdateRequest.getStartDate().isAfter(eventUpdateRequest.getEndDate())){
                throw new BadRequestException("Invalid dates given");
            }
            event.setEndDate(eventUpdateRequest.getEndDate());
        }

        Event updatedEvent = eventRepo.save(event);
        return new EventResponse(updatedEvent);
    }

    public EventResponse updateEventStallBluePrint(UUID eventId, MultipartFile blueprintFile) throws IOException {

        if (blueprintFile.isEmpty()) {
            throw new BadRequestException("Blueprint file is empty");
        }

        Event event = getEvent(eventId);
        event.setStallsBluePrint(blueprintFile.getBytes());
        event.setStallsBluePrintContentType(blueprintFile.getContentType());

        Event updatedEvent = eventRepo.save(event);
        return new EventResponse(updatedEvent);
    }

    public EventResponse getEventById(UUID eventId) {
        Event event = eventRepo.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));
        return new EventResponse(event);
    }

    public Event getEvent(UUID eventId) {
        return eventRepo.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));
    }
}