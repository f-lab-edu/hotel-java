package com.hotelJava.reservation.application.service;

import com.hotelJava.reservation.application.port.in.result.ConfirmedReservationHistory;
import com.hotelJava.reservation.domain.Reservation;
import com.hotelJava.reservation.domain.ReserveType;
import java.util.Optional;

public interface ConfirmHandler {
  boolean supports(ReserveType reserveType);

  Optional<ConfirmedReservationHistory> confirm(Reservation reservation, String paymentApiNo);
}
