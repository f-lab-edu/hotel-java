package com.hotelJava.reservation.application.port.out.persistence;

import com.hotelJava.reservation.domain.Reservation;

public interface SaveReservationPort {
  Reservation save(Reservation reservation);
}
