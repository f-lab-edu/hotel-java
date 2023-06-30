package com.hotelJava.reservation.application.port;

import com.hotelJava.reservation.domain.ReservationCommand;
import com.hotelJava.reservation.dto.CreateReservationRequest;
import com.hotelJava.reservation.dto.CreateReservationResponse;

public interface CreateReservationUseCase {

  boolean supports(ReservationCommand reservationCommand);

  CreateReservationResponse createReservation(
      Long roomId, String email, CreateReservationRequest createReservationRequest);
}
