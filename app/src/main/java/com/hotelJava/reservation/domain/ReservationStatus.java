package com.hotelJava.reservation.domain;

public enum ReservationStatus {

    RESERVATION("예약"), CANCEL("취소");

    private final String value;

    ReservationStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
