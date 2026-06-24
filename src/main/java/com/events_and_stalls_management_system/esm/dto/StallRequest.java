package com.events_and_stalls_management_system.esm.dto;

import com.events_and_stalls_management_system.esm.entity.StallStatus;
import com.events_and_stalls_management_system.esm.entity.StallType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class StallRequest {

    private String stallNumber;
    private StallType stallType;
    private BigDecimal price;
}
