package com.hotelJava.reservation.application.port;

import com.hotelJava.reservation.domain.ReservationCommand;

public interface FindReservationQuery {

    CreateReservationUseCase findService(ReservationCommand reservationCommand);
}
