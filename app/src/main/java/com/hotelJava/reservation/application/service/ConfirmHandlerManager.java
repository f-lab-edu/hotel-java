package com.hotelJava.reservation.application.service;

import com.hotelJava.common.error.ErrorCode;
import com.hotelJava.common.error.exception.BadRequestException;
import com.hotelJava.reservation.application.port.in.ConfirmUseCase;
import com.hotelJava.reservation.application.port.in.result.ConfirmedReservationHistory;
import com.hotelJava.reservation.application.port.out.persistence.FindReservationPort;
import com.hotelJava.reservation.domain.Reservation;
import com.hotelJava.reservation.domain.ReserveType;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ConfirmHandlerManager implements ConfirmUseCase {

  private final List<ConfirmHandler> confirmHandlers;
  private final FindReservationPort findReservationPort;

  public Optional<ConfirmedReservationHistory> confirm(String reservationNo, String paymentApiNo) {
    Reservation reservation = findReservationPort.findByReservationNo(reservationNo);

    ConfirmHandler confirmHandler = findConfirmHandler(reservation.getReserveType());

    return confirmHandler.confirm(reservation, paymentApiNo);
  }

  private ConfirmHandler findConfirmHandler(ReserveType reserveType) {
    return confirmHandlers.stream()
        .filter(service -> service.supports(reserveType))
        .findFirst()
        .orElseThrow(() -> new BadRequestException(ErrorCode.BAD_REQUEST_ERROR));
  }
}
