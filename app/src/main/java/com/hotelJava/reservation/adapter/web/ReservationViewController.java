package com.hotelJava.reservation.adapter.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReservationViewController {

  @GetMapping("/reservations")
  public String reservationForm() {
    return "reservation/reservation";
  }
}
