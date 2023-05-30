package com.hotelJava.reservation.adapter.out.api;

import com.hotelJava.reservation.application.port.out.api.CancelPaymentPort;
import com.hotelJava.reservation.application.port.out.api.VerifyPaymentAmountPort;

public interface PaymentAdapter extends CancelPaymentPort, VerifyPaymentAmountPort {}
