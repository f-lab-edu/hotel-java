package com.hotelJava.reservation.controller;

import com.hotelJava.common.dto.DecodeId;
import com.hotelJava.reservation.dto.CreateReservationRequestDto;
import com.hotelJava.reservation.service.ReservationService;
import com.hotelJava.reservation.service.ReservationServiceManager;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reservations")
public class ReservationController {

  private final ReservationServiceManager reservationServiceManager;

  @PostMapping("{encodedAccommodationId}/{encodedRoomId}")
  public void createReservation(
      @PathVariable("encodedAccommodationId") DecodeId accommodationId,
      @PathVariable("encodedRoomId") DecodeId roomId,
      @Valid @RequestBody CreateReservationRequestDto createReservationRequestDto) {
    ReservationService reservationService =
        reservationServiceManager.findService(createReservationRequestDto.getReservationCommand());

    reservationService.saveReservation(
            accommodationId.getDecodeId(), roomId.getDecodeId(), createReservationRequestDto);
  }
}
