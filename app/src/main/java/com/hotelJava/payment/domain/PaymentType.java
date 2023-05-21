package com.hotelJava.payment.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PaymentType {
  KAKAO_PAY("카카오페이"),
  NAVER_PAY("네이버페이"),
  TOSS_PAY("토스페이"),
  PAYCO("PAYCO"),
  CARD("신용/체크카트"),
  PHONE("휴대폰결제");

  private final String label;
}
