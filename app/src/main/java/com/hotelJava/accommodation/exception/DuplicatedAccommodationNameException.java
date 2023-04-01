package com.hotelJava.accommodation.exception;

public class DuplicatedAccommodationNameException extends RuntimeException {

    public DuplicatedAccommodationNameException() {
        super();
    }

    public DuplicatedAccommodationNameException(String message) {
        super(message);
    }

    public DuplicatedAccommodationNameException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicatedAccommodationNameException(Throwable cause) {
        super(cause);
    }

    public DuplicatedAccommodationNameException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
