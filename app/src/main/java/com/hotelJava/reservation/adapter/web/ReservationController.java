package com.hotelJava.reservation.adapter.web;

import com.hotelJava.common.dto.DecodeId;
import com.hotelJava.reservation.application.port.FindReservationQuery;
import com.hotelJava.reservation.dto.CreateReservationRequest;
import com.hotelJava.reservation.dto.CreateReservationResponse;
import com.hotelJava.reservation.application.port.CreateReservationUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

  private final FindReservationQuery findReservationQuery;

  @PostMapping("/{encodedRoomId}")
  public CreateReservationResponse createReservation(
      @PathVariable("encodedRoomId") DecodeId roomId,
      @AuthenticationPrincipal(expression = "email") String loginEmail,
      @Valid @RequestBody CreateReservationRequest dto) throws InterruptedException {
    CreateReservationUseCase createReservationUseCase =
        findReservationQuery.findService(dto.getReservationCommand());

    return createReservationUseCase.createReservation(roomId.getDecodeId(), loginEmail, dto);
  }
}
