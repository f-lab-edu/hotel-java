package com.hotelJava.reservation.domain;

public enum ReservationStatus {

    RESERVATION_AVAILABLE("예약가능"), SALES_COMPLETED("판매완료");

    private final String value;

    ReservationStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
