package com.hotelJava.reservation.service;

import com.hotelJava.reservation.domain.ReservationCommand;
import com.hotelJava.reservation.dto.CreateReservationRequestDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public interface ReservationService {

  boolean supports(ReservationCommand reservationCommand);

  void saveReservation(
      Long accommodationId,
      Long roomId,
      CreateReservationRequestDto createReservationRequestDto);
}
