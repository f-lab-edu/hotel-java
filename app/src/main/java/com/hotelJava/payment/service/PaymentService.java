package com.hotelJava.payment.service;

import com.hotelJava.payment.dto.CreatePaymentRequestDto;

public interface PaymentService {

  void createPayment(Long roomId, CreatePaymentRequestDto dto);
}
