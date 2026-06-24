package com.events_and_stalls_management_system.esm.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "bookings")
@Setter
@Getter
public class Booking {

    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    private UUID bookingId;

    @ManyToOne
    @JoinColumn(name = "stall_id")
    private Stall stall;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;

    private String name;
    private String mobileNumber;
    private String category;
    @Column(length = 500)
    private String description;
    private String razorpayOrderId;
    private String razorpayPaymentId;
    private String bookingReference;
    private LocalDateTime createdAt;
    private String razorpaySignature;
}
