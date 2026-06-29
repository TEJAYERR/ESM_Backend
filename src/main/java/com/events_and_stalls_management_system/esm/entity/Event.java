package com.events_and_stalls_management_system.esm.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Blob;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity(name = "events")
@Setter
@Getter
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String venue;
    private LocalDate startDate;
    private LocalDate endDate;

    @Column(length = 500)
    private String description;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] stallsBluePrint;

    @Enumerated(EnumType.STRING)
    private EventStatus eventStatus = EventStatus.OPEN;

    private String stallsBluePrintContentType;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Stall> stalls;
}
