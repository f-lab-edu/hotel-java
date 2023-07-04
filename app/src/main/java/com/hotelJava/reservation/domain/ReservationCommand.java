package com.hotelJava.reservation.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ReservationCommand {

    EAGER_RESERVATION, LAZY_RESERVATION
}
