package com.events_and_stalls_management_system.esm.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Entity(name = "stalls")
@Setter
@Getter
@Table(
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"stallNumber", "event_id"})
        }
)
public class Stall {

    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    private UUID id;

    @Enumerated(EnumType.STRING)
    private StallType stallType;

    @Enumerated(EnumType.STRING)
    private StallStatus stallStatus = StallStatus.AVAILABLE;

    private String stallNumber;

    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @OneToMany(mappedBy = "stall", cascade = CascadeType.ALL)
    private List<Booking> booking;
}
