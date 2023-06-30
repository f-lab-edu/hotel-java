package com.hotelJava.reservation.application.port;

import com.hotelJava.reservation.domain.ReservationCommand;

public interface FindServiceUseCase {

    CreateReservationUseCase findService(ReservationCommand reservationCommand);
}
