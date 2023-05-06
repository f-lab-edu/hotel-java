package com.hotelJava.payment.controller;

import com.hotelJava.payment.dto.CreatePaymentRequestDto;
import com.hotelJava.payment.dto.ImpUidDto;
import com.hotelJava.payment.service.PaymentService;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payments")
public class PaymentController {

  private final PaymentService paymentService;

  @PostMapping("/verify-iamport")
  public IamportResponse<Payment> verifyIamport(@RequestBody ImpUidDto impUidDto) {
    return paymentService.verifyIamport(impUidDto);
  }

  @PostMapping
  public void savePayment(@Valid @RequestBody CreatePaymentRequestDto createPaymentRequestDto) {
    paymentService.savePayment(createPaymentRequestDto);
  }
}
