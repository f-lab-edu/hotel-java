package com.hotelJava.reservation.controller;

import com.hotelJava.common.dto.DecodeId;
import com.hotelJava.reservation.dto.CreateReservationRequestDto;
import com.hotelJava.reservation.dto.CreateReservationResponseDto;
import com.hotelJava.reservation.service.ReservationService;
import com.hotelJava.reservation.service.ReservationServiceManager;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

  @PostMapping("/{encodedRoomId}")
  public CreateReservationResponseDto createReservation(
      @PathVariable("encodedRoomId") DecodeId roomId,
      @AuthenticationPrincipal(expression = "email") String loginEmail,
      @Valid @RequestBody CreateReservationRequestDto dto) {
    ReservationService reservationService =
        reservationServiceManager.findService(dto.getReservationCommand());

    return reservationService.createReservation(roomId.getDecodeId(), loginEmail, dto);
  }
}
