package com.hotelJava.payment.controller;

import com.hotelJava.common.dto.DecodeId;
import com.hotelJava.payment.dto.CreatePaymentRequestDto;
import com.hotelJava.payment.service.PaymentByKakaoPay;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payments")
public class PaymentController {

  private final PaymentByKakaoPay paymentService;

  @PostMapping("/{encodedRoomId}")
  public void createPayment(
      @PathVariable("encodedRoomId") DecodeId roomId,
      @Valid @RequestBody CreatePaymentRequestDto dto) {
    paymentService.createPayment(roomId.getDecodeId(), dto);
  }
}
