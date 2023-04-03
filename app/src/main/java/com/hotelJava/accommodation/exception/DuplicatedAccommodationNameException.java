package com.hotelJava.accommodation.exception;

public class DuplicatedAccommodationNameException extends RuntimeException {

    public DuplicatedAccommodationNameException() {
        super();
    }

    public DuplicatedAccommodationNameException(String message) {
        super(message);
    }
}
