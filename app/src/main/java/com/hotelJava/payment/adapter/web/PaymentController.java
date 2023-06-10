package com.hotelJava.payment.adapter.web;

import com.hotelJava.common.dto.DecodeId;
import com.hotelJava.payment.application.port.CreatePaymentUseCase;
import com.hotelJava.payment.dto.CreatePaymentRequest;
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

  private final CreatePaymentUseCase createPaymentUseCase;

  @PostMapping("/{encodedRoomId}")
  public void createPayment(
      @PathVariable("encodedRoomId") DecodeId roomId,
      @Valid @RequestBody CreatePaymentRequest dto) {
    createPaymentUseCase.createPayment(roomId.getDecodeId(), dto);
  }
}
