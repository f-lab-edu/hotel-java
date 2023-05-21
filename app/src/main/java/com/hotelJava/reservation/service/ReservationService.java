package com.hotelJava.reservation.service;

import com.hotelJava.reservation.domain.ReservationCommand;
import com.hotelJava.reservation.dto.CreateReservationRequestDto;
import com.hotelJava.reservation.dto.CreateReservationResponseDto;

public interface ReservationService {

  boolean supports(ReservationCommand reservationCommand);

  CreateReservationResponseDto createReservation(
      Long roomId, String email, CreateReservationRequestDto createReservationRequestDto);
}
