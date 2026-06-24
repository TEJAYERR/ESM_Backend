package com.events_and_stalls_management_system.esm.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
public class EventRequest {

    private String name;
    private String venue;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
    List<StallRequest> stallRequests;
}
