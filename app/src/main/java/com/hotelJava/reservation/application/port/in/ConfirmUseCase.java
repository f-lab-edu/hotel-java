package com.hotelJava.reservation.application.port.in;

import com.hotelJava.reservation.application.port.in.result.ConfirmedReservationHistory;
import java.util.Optional;

public interface ConfirmUseCase {
  Optional<ConfirmedReservationHistory> confirm(String reservationNo, String paymentApiNo);
}
