package com.hotelJava.reservation.service;

import com.hotelJava.reservation.domain.ReservationCommand;
import com.hotelJava.reservation.dto.CreateReservationRequestDto;

public class HojunReservationService implements ReservationService {

  @Override
  public boolean supports(ReservationCommand reservationCommand) {
    return reservationCommand.equals(ReservationCommand.HOJUN_RESERVATION);
  }

  @Override
  public void saveReservation(
      Long accommodationId, Long roomId, CreateReservationRequestDto createReservationRequestDto) {}
}
