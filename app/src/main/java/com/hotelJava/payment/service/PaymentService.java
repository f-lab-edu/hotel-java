package com.hotelJava.payment.service;

import com.hotelJava.member.domain.Member;
import com.hotelJava.payment.dto.CreatePaymentRequestDto;

public interface PaymentService {

  void createPayment(Long roomId, Member member, CreatePaymentRequestDto dto);
}
