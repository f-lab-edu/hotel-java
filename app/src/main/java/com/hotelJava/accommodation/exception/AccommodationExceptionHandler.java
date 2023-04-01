package com.hotelJava.accommodation.exception;

import com.hotelJava.common.exception.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackages = {"com.hotelJava.accommodation"})
public class AccommodationExceptionHandler {

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DuplicatedAccommodationNameException.class)
    public ErrorResult duplicatedAccommodationNameHandler(DuplicatedAccommodationNameException e) {
        return new ErrorResult("CONFLICT", "숙소명이 이미 존재합니다.");
    }
}
