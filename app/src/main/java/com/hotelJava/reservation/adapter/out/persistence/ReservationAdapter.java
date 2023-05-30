package com.hotelJava.reservation.adapter.out.persistence;

import com.hotelJava.common.error.ErrorCode;
import com.hotelJava.common.error.exception.BadRequestException;
import com.hotelJava.reservation.application.port.out.persistence.FindReservationPort;
import com.hotelJava.reservation.application.port.out.persistence.SaveReservationPort;
import com.hotelJava.reservation.domain.Reservation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ReservationAdapter implements SaveReservationPort, FindReservationPort {

  private final ReservationRepository reservationRepository;

  @Override
  public Reservation save(Reservation reservation) {
    return reservationRepository.save(reservation);
  }

  @Override
  public Reservation findByReservationNo(String reservationNo) {
    return reservationRepository
        .findByReservationNo(reservationNo)
        .orElseThrow(() -> new BadRequestException(ErrorCode.RESERVATION_NOT_FOUND));
  }
}
