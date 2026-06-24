package com.events_and_stalls_management_system.esm.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingRequest {

    String vendorName;
    String mobileNumber;
    String description;
    String category;
}
