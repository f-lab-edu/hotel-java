package com.hotelJava.payment.service;

import com.hotelJava.payment.dto.CreatePaymentRequestDto;
import com.hotelJava.payment.dto.ImpUidDto;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;

public interface PaymentService {

  IamportResponse<Payment> verifyIamport(ImpUidDto impUidDto);
  
  void savePayment(CreatePaymentRequestDto createPaymentRequestDto);
}
