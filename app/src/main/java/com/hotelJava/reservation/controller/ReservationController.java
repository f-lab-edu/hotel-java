package com.hotelJava.reservation.controller;

import com.hotelJava.common.error.ErrorCode;
import com.hotelJava.common.error.exception.InternalServerException;
import com.hotelJava.reservation.dto.CreateReservationRequestDto;
import com.hotelJava.reservation.service.ReservationService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reservations")
public class ReservationController {

  private final List<ReservationService> reservationServices;

  @PostMapping
  public void createReservation(
      @RequestBody CreateReservationRequestDto createReservationRequestDto) {
    ReservationService reservationService =
        reservationServices.stream()
            .filter(
                bookingService ->
                    bookingService.supports(createReservationRequestDto.getReservationCommand()))
            .findFirst()
            .orElseThrow(() -> new InternalServerException(ErrorCode.INTERNAL_SERVER_ERROR));

    reservationService.createReservation(createReservationRequestDto);
  }
}
