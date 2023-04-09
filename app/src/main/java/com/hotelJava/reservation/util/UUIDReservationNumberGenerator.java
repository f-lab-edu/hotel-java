package com.hotelJava.reservation.util;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class UUIDReservationNumberGenerator implements ReservationNumberGenerator {

    @Override
    public String generateReservationNumber() {
        return UUID.randomUUID().toString() + LocalDateTime.now();
    }
}
