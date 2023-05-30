package com.hotelJava.reservation.application.port.out.api;

public interface VerifyPaymentAmountPort {
  boolean verify(String paymentApiId, long billingAmount);
}
