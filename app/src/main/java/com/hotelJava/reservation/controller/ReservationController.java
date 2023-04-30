package com.hotelJava.reservation.controller;

import com.hotelJava.reservation.dto.CreateReservationRequestDto;
import com.hotelJava.reservation.service.ReservationService;
import com.hotelJava.reservation.service.ReservationServiceManager;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reservations")
public class ReservationController {

  private final ReservationServiceManager reservationServiceManager;

  @PostMapping("{encodedAccommodationId}/{encodedRoomId}")
  public void createReservation(
      @PathVariable String encodedAccommodationId,
      @PathVariable String encodedRoomId,
      @Valid @RequestBody CreateReservationRequestDto createReservationRequestDto) {
    ReservationService reservationService =
        reservationServiceManager.findService(createReservationRequestDto.getReservationCommand());

    reservationService.saveReservation(
        encodedAccommodationId, encodedRoomId, createReservationRequestDto);
  }
}
