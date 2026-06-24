package com.events_and_stalls_management_system.esm.service;

import com.events_and_stalls_management_system.esm.dto.*;
import com.events_and_stalls_management_system.esm.entity.*;
import com.events_and_stalls_management_system.esm.exception.BadRequestException;
import com.events_and_stalls_management_system.esm.exception.ResourceNotFoundException;
import com.events_and_stalls_management_system.esm.repository.BookingRepo;
import com.events_and_stalls_management_system.esm.repository.StallRepo;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import jakarta.transaction.Transactional;
import org.json.JSONObject;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class StallService {

    private final StallRepo stallRepo;
    private final EventService eventService;
    private final BookingRepo bookingRepo;

    public StallService(StallRepo stallRepo, EventService eventService, BookingRepo bookingRepo) {
        this.stallRepo = stallRepo;
        this.eventService = eventService;
        this.bookingRepo = bookingRepo;
    }

    //getting stall by stallId and eventId
    public StallResponse getStallById(UUID eventId, UUID stallId) {

        Stall stall = getStall(eventId, stallId);
        return new StallResponse(stall);
    }

    //adding a stall to event
    public StallResponse addStall(UUID eventId, StallRequest stallRequest) {

        Event event = eventService.getEvent(eventId);

        Stall stall = new Stall();

        stall.setStallNumber(stallRequest.getStallNumber());
        stall.setStallType(stallRequest.getStallType());
        stall.setPrice(stallRequest.getPrice());
        stall.setEvent(event);

        Stall savedStall = stallRepo.save(stall);
        return new StallResponse(savedStall);
    }

    //updating a stall details of an event
    public StallResponse updateStall(UUID eventId, UUID stallId, StallRequest stallRequest) {

        Stall stall = getStall(eventId, stallId);

        Event event = stall.getEvent();

        stall.setStallNumber(stallRequest.getStallNumber());
        stall.setStallType(stallRequest.getStallType());
        stall.setPrice(stallRequest.getPrice());
        stall.setEvent(event);

        Stall savedStall = stallRepo.save(stall);
        return new StallResponse(savedStall);
    }

    //delete a stall of event
    public StallResponse deleteStall(UUID eventId, UUID stallId) {

        Stall stall = getStall(eventId, stallId);

        StallResponse response = new StallResponse(stall);

        stallRepo.delete(stall);

        return response;
    }

    //booking a stall of an event
    @Transactional
    public BookingResponse createBooking(UUID eventId, UUID stallId, BookingRequest bookingRequest) throws RazorpayException {

        Stall stall = getStall(eventId, stallId);

        if(stall.getStallStatus() != StallStatus.AVAILABLE){
            throw new BadRequestException("Already booked");
        }

        Booking booking = new Booking();
        booking.setName(bookingRequest.getVendorName());
        booking.setCategory(bookingRequest.getCategory());
        booking.setDescription(bookingRequest.getDescription());
        booking.setMobileNumber(bookingRequest.getMobileNumber());
        booking.setCreatedAt(LocalDateTime.now());
        booking.setStall(stall);
        booking.setAmount(stall.getPrice());
        booking.setBookingStatus(BookingStatus.PENDING);

        booking = bookingRepo.save(booking);

        RazorpayClient client = new RazorpayClient("rzp_test_SfGIn89zRbphHF", "Z4r3xGX3UK3R71acSgmpN9sz");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("amount", stall.getPrice()
                                        .multiply(BigDecimal.valueOf(100))
                                        .intValue());
        jsonObject.put("currency", "INR");
        jsonObject.put("receipt", booking.getBookingId().toString());

        booking.setBookingReference(
                "BK-" + System.currentTimeMillis()
        );

        Order razorpayOrder = client.orders.create(jsonObject);
        booking.setRazorpayOrderId(razorpayOrder.get("id"));
        bookingRepo.save(booking);

        return new BookingResponse(booking);
    }

    @Transactional(rollbackOn = Exception.class)
    public String verifyPayment(UUID eventId, UUID stallId, PaymentVerificationRequest paymentVerificationRequest) throws Exception {

        Stall stall = getStall(eventId, stallId);

        String data = paymentVerificationRequest.getRazorpayOrderId() + "|" + paymentVerificationRequest.getRazorpayPaymentId();
        String generatedSignature = hmacSha256(data, "Z4r3xGX3UK3R71acSgmpN9sz");

        Booking booking = bookingRepo.findByStallAndRazorpayOrderId(stall, paymentVerificationRequest.getRazorpayOrderId())
                .orElseThrow(() -> new ResourceNotFoundException("booking not found"));

        if(booking.getBookingStatus() == BookingStatus.CONFIRMED){
            return "Already verified";
        }

        if(stall.getStallStatus() != StallStatus.AVAILABLE){
            throw new BadRequestException("Already booked");
        }

        if(!generatedSignature.equals(paymentVerificationRequest.getRazorpaySignature())) {

            booking.setBookingStatus(BookingStatus.FAILED);
            bookingRepo.save(booking);

            return "payment verification failed";
        }

        booking.setBookingStatus(BookingStatus.CONFIRMED);
        stall.setStallStatus(StallStatus.BOOKED);
        booking.setRazorpayPaymentId(paymentVerificationRequest.getRazorpayPaymentId());
        booking.setRazorpaySignature(paymentVerificationRequest.getRazorpaySignature());

        bookingRepo.save(booking);
        stallRepo.save(stall);

        return "success";
    }

    private Stall getStall(UUID eventId, UUID stallId) {

        return stallRepo.findByIdAndEventId(stallId, eventId)
                .orElseThrow(() -> new ResourceNotFoundException("stall not found"));
    }

    private static String hmacSha256(String data, String key) throws Exception {
        javax.crypto.Mac mac = javax.crypto.Mac.getInstance("HmacSHA256");
        javax.crypto.spec.SecretKeySpec secretKey =
                new javax.crypto.spec.SecretKeySpec(key.getBytes(), "HmacSHA256");
        mac.init(secretKey);
        byte[] rawHmac = mac.doFinal(data.getBytes());
        return org.apache.commons.codec.binary.Hex.encodeHexString(rawHmac);
    }
}
