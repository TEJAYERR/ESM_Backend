package com.events_and_stalls_management_system.esm.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingUpdateRequest {
    private String name;
    private String mobileNumber;
    private String category;
    private String description;
}
