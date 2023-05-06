package com.hotelJava.reservation.service;

import com.hotelJava.common.error.ErrorCode;
import com.hotelJava.common.error.exception.BadRequestException;
import com.hotelJava.reservation.domain.ReservationCommand;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservationServiceManager {

  private final List<ReservationService> reservationServices;

  public ReservationService findService(ReservationCommand reservationCommand) {
    return reservationServices.stream()
        .filter(bookingService -> bookingService.supports(reservationCommand))
        .findFirst()
        .orElseThrow(() -> new BadRequestException(ErrorCode.BAD_REQUEST_ERROR));
  }
}
