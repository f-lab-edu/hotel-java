package com.hotelJava.payment.domain;

public enum PaymentStatus {
  WAITING("결제대기"),
  COMPLETE("결제완료"),
  CANCEL("결제취소"),
  ERROR("결제오류");

  private final String label;

  PaymentStatus(String label) {
    this.label = label;
  }
}
