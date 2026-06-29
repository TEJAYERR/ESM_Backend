package com.events_and_stalls_management_system.esm.dto;

import com.events_and_stalls_management_system.esm.entity.Event;
import com.events_and_stalls_management_system.esm.entity.EventStatus;
import com.events_and_stalls_management_system.esm.entity.Stall;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class EventResponse {

    private UUID id;
    private String name;
    private String venue;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private EventStatus eventStatus;
    private List<StallResponse> stalls;

    public EventResponse(Event event) {
        this.id = event.getId();
        this.name = event.getName();
        this.venue = event.getVenue();
        this.description = event.getDescription();
        this.startDate = event.getStartDate();
        this.endDate = event.getEndDate();
        this.eventStatus = event.getEventStatus();
        this.stalls = new ArrayList<>();
        for (Stall stall : event.getStalls()) {
            this.stalls.add(new StallResponse(stall));
        }
    }
}