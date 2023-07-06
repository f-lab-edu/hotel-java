package com.hotelJava.reservation.application.service;

import com.hotelJava.reservation.application.port.CreateReservationUseCase;
import com.hotelJava.reservation.domain.ReservationCommand;
import com.hotelJava.reservation.dto.CreateReservationRequest;
import com.hotelJava.reservation.dto.CreateReservationResponse;

public class LazyReservationService implements CreateReservationUseCase {

  @Override
  public boolean supports(ReservationCommand reservationCommand) {
    return reservationCommand.equals(ReservationCommand.LAZY_RESERVATION);
  }

  @Override
  public CreateReservationResponse createReservation(
      Long roomId, String email, CreateReservationRequest createReservationRequest) {
    return null;
  }
}
