package com.hotelJava.reservation.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import net.bytebuddy.utility.RandomString;
import org.springframework.stereotype.Component;

@Component
public class RandomReservationNumberGenerator implements ReservationNumberGenerator {

  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

  @Override
  public String generateReservationNumber() {
    Random r = new Random();
    RandomString rs = new RandomString(5, r);

    return rs.nextString() + LocalDateTime.now().format(formatter);
  }
}
