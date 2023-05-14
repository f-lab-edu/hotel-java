package com.hotelJava.reservation.service;

import com.hotelJava.member.domain.Member;
import com.hotelJava.reservation.domain.ReservationCommand;
import com.hotelJava.reservation.dto.CreateReservationRequestDto;

public interface ReservationService {

  boolean supports(ReservationCommand reservationCommand);

  void createReservation(
      Long roomId, Member member, CreateReservationRequestDto createReservationRequestDto);
}
