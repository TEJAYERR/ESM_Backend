package com.events_and_stalls_management_system.esm.dto;

import com.events_and_stalls_management_system.esm.entity.EventStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventStatusUpdate {
    private EventStatus eventStatus;
}
