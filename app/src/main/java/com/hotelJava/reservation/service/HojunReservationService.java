package com.hotelJava.reservation.service;

import com.hotelJava.reservation.domain.ReservationCommand;
import com.hotelJava.reservation.dto.CreateReservationRequestDto;
import com.hotelJava.reservation.dto.CreateReservationResponseDto;

public class HojunReservationService implements ReservationService {

  @Override
  public boolean supports(ReservationCommand reservationCommand) {
    return reservationCommand.equals(ReservationCommand.HOJUN_RESERVATION);
  }

  @Override
  public CreateReservationResponseDto createReservation(
      Long roomId, String email, CreateReservationRequestDto createReservationRequestDto) {
    return null;
  }
}
