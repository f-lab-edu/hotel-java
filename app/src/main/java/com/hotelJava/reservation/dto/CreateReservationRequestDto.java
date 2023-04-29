package com.hotelJava.reservation.dto;

import com.hotelJava.reservation.domain.ReservationCommand;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CreateReservationRequestDto {

  private ReservationCommand reservationCommand;
}
