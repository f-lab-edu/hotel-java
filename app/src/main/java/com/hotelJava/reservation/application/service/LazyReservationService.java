package com.hotelJava.reservation.application.service;

import com.hotelJava.reservation.application.port.CreateReservationUseCase;
import com.hotelJava.reservation.domain.ReservationCommand;
import com.hotelJava.reservation.dto.CreateReservationRequest;
import com.hotelJava.reservation.dto.CreateReservationResponse;

/**
 * 결제가 먼저 완료되어야 예약이 되는 방식의 예약 클래스 (결제가 늦어지면 다른 사용자에게 룸이 빼앗길 수 있다.)
 */
public class LazyReservationService implements CreateReservationUseCase {

    @Override
    public boolean supports(ReservationCommand reservationCommand) {
        return reservationCommand.equals(ReservationCommand.LAZY_RESERVATION);
    }

    @Override
    public CreateReservationResponse createReservation(
            Long roomId, String email, CreateReservationRequest createReservationRequest) {
        return null;
    }
}
