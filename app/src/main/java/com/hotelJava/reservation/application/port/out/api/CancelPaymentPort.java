package com.hotelJava.reservation.application.port.out.api;



public interface CancelPaymentPort {
  boolean cancel(String paymentApiId);
}
