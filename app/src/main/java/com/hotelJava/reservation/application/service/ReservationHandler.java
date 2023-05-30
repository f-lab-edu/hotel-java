package com.hotelJava.reservation.application.service;

import com.hotelJava.reservation.application.port.in.command.ReserveCommand;
import com.hotelJava.reservation.application.port.in.result.ReserveResult;
import com.hotelJava.reservation.domain.ReserveType;

public interface ReservationHandler {
  boolean supports(ReserveType reserveType);

  ReserveResult reserve(String email, Long roomId, ReserveCommand reserveCommand);
}
