package com.hotelJava.reservation.application.service;

import com.hotelJava.common.error.ErrorCode;
import com.hotelJava.common.error.exception.BadRequestException;
import com.hotelJava.reservation.application.port.CreateReservationUseCase;
import com.hotelJava.reservation.application.port.FindServiceUseCase;
import com.hotelJava.reservation.domain.ReservationCommand;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservationServiceManager implements FindServiceUseCase {

  private final List<CreateReservationUseCase> createReservationUseCases;

  public CreateReservationUseCase findService(ReservationCommand reservationCommand) {
    return createReservationUseCases.stream()
        .filter(createReservationUseCase -> createReservationUseCase.supports(reservationCommand))
        .findFirst()
        .orElseThrow(() -> new BadRequestException(ErrorCode.BAD_REQUEST_ERROR));
  }
}
