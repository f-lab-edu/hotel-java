package com.hotelJava.reservation.controller;

import com.hotelJava.common.error.ErrorCode;
import com.hotelJava.common.error.exception.InternalServerException;
import com.hotelJava.reservation.dto.CreateReservationRequestDto;
import com.hotelJava.reservation.service.ReservationService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reservations")
public class ReservationController {

  private final List<ReservationService> reservationServices;

  @PostMapping("{encodedAccommodationId}/{encodedRoomId}")
  public void createReservation(
      @PathVariable String encodedAccommodationId,
      @PathVariable String encodedRoomId,
      @Valid @RequestBody CreateReservationRequestDto createReservationRequestDto) {
    ReservationService reservationService =
        reservationServices.stream()
            .filter(
                bookingService ->
                    bookingService.supports(createReservationRequestDto.getReservationCommand()))
            .findFirst()
            .orElseThrow(() -> new InternalServerException(ErrorCode.INTERNAL_SERVER_ERROR));

    reservationService.saveReservation(encodedAccommodationId, encodedRoomId, createReservationRequestDto);
  }
}
