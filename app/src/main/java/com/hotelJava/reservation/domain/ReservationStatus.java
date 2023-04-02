package com.hotelJava.reservation.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ReservationStatus {

    RESERVATION_AVAILABLE("예약가능"),
    SALES_COMPLETED("판매완료"),
    RESERVATION_COMPLETED("예약완료");

    private final String value;
}
