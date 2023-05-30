package com.hotelJava.reservation.domain;

import com.hotelJava.common.embeddable.Money;
import java.time.LocalDateTime;

public interface PaymentInfo {
  PaymentStatus getPaymentStatus();

  Money getAmount();

  LocalDateTime getPaymentDate();
}
