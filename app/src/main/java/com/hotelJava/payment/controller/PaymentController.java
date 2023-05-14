package com.hotelJava.payment.controller;

import com.hotelJava.common.dto.DecodeId;
import com.hotelJava.member.domain.Member;
import com.hotelJava.payment.dto.CreatePaymentRequestDto;
import com.hotelJava.payment.service.PaymentByKakaoPay;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payments")
public class PaymentController {

  private final PaymentByKakaoPay paymentService;

  @PostMapping("/{encodedRoomId}")
  public void createPayment(
      @PathVariable("encodedRoomId") DecodeId roomId,
      @AuthenticationPrincipal Member member,
      @Valid @RequestBody CreatePaymentRequestDto dto) {
    paymentService.createPayment(roomId.getDecodeId(), member, dto);
  }
}
