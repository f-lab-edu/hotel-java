package com.hotelJava.reservation.util;

import net.bytebuddy.utility.RandomString;import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@Component
public class RandomReservationNumberGenerator implements ReservationNumberGenerator {

    private LocalDateTime now = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    @Override
    public String generateReservationNumber() {
        Random r = new Random();
        RandomString rs = new RandomString(5, r);

        return rs.nextString() + now.format(formatter);
    }
}
