package com.hotelJava.reservation.controller;

import com.hotelJava.common.dto.DecodeId;
import com.hotelJava.member.domain.Member;
import com.hotelJava.reservation.dto.CreateReservationRequestDto;
import com.hotelJava.reservation.service.ReservationService;
import com.hotelJava.reservation.service.ReservationServiceManager;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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
  public ResponseEntity<String> createReservation(@PathVariable("encodedRoomId") DecodeId roomId,
    @AuthenticationPrincipal Member member, @Valid @RequestBody CreateReservationRequestDto dto) {
    ReservationService reservationService = reservationServiceManager.findService(dto.getReservationCommand());
    reservationService.createReservation(roomId.getDecodeId(), member, dto);

    return ResponseEntity.notFound().build();
  }
}
