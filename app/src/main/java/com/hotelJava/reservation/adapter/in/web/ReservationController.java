package com.hotelJava.reservation.adapter.in.web;

import com.hotelJava.common.dto.DecodeId;
import com.hotelJava.reservation.application.port.in.ConfirmUseCase;
import com.hotelJava.reservation.application.port.in.ReserveUseCase;
import com.hotelJava.reservation.application.port.in.command.ReserveCommand;
import com.hotelJava.reservation.application.port.in.result.ReserveResult;
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

  private final ReserveUseCase reserveUseCase;
  private final ConfirmUseCase confirmUseCase;

  @PostMapping("/{encodedRoomId}")
  public ReserveResult reserve(
      @PathVariable("encodedRoomId") DecodeId roomId,
      @AuthenticationPrincipal(expression = "email") String loginEmail,
      @Valid @RequestBody ReserveCommand reserveCommand) {
    return reserveUseCase.reserve(loginEmail, roomId.getDecodeId(), reserveCommand);
  }
}
