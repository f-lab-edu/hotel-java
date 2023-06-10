package com.hotelJava.payment.application.port;

import com.hotelJava.payment.dto.CreatePaymentRequest;

public interface CreatePaymentUseCase {

    void createPayment(Long roomId, CreatePaymentRequest dto);
}
