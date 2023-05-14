package com.hotelJava.reservation.service;

import com.hotelJava.member.domain.Member;
import com.hotelJava.reservation.domain.ReservationCommand;
import com.hotelJava.reservation.dto.CreateReservationRequestDto;

public class HojunReservationService implements ReservationService {

  @Override
  public boolean supports(ReservationCommand reservationCommand) {
    return reservationCommand.equals(ReservationCommand.HOJUN_RESERVATION);
  }

  @Override
  public void createReservation(
      Long roomId, Member member, CreateReservationRequestDto createReservationRequestDto) {}
}
