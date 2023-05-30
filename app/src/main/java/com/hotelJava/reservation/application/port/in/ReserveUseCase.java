package com.hotelJava.reservation.application.port.in;

import com.hotelJava.reservation.application.port.in.command.ReserveCommand;
import com.hotelJava.reservation.application.port.in.result.ReserveResult;

public interface ReserveUseCase {
  ReserveResult reserve(String email, Long roomId, ReserveCommand reserveCommand);
}
