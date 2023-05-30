package com.hotelJava.reservation.application.service;

import com.hotelJava.common.error.ErrorCode;
import com.hotelJava.common.error.exception.BadRequestException;
import com.hotelJava.reservation.application.port.in.ReserveUseCase;
import com.hotelJava.reservation.application.port.in.command.ReserveCommand;
import com.hotelJava.reservation.application.port.in.result.ReserveResult;
import com.hotelJava.reservation.domain.ReserveType;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Component
public class ReservationHandlerManager implements ReserveUseCase {

  private final List<ReservationHandler> reservationHandlers;

  @Override
  @Transactional
  public ReserveResult reserve(String email, Long roomId, ReserveCommand reserveCommand) {
    ReservationHandler reservationHandler = getReservationHandler(reserveCommand.getReserveType());
    return reservationHandler.reserve(email, roomId, reserveCommand);
  }

  private ReservationHandler getReservationHandler(ReserveType reservationCommand) {
    return reservationHandlers.stream()
        .filter(service -> service.supports(reservationCommand))
        .findFirst()
        .orElseThrow(() -> new BadRequestException(ErrorCode.BAD_REQUEST_ERROR));
  }
}
