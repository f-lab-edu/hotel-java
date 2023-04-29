package com.hotelJava.reservation.service;

import com.hotelJava.reservation.domain.ReservationCommand;
import com.hotelJava.reservation.dto.CreateReservationRequestDto;
import org.springframework.stereotype.Service;

@Service
public interface ReservationService {

  boolean supports(ReservationCommand reservationCommand);

  void createReservation(CreateReservationRequestDto createReservationRequestDto);
}
