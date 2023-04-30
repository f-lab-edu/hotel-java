package com.hotelJava.payment.dto;

import com.hotelJava.payment.domain.PaymentType;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CreatePaymentRequestDto {

  @NotNull(message = "결제수단을 선택해야 합니다.")
  private PaymentType paymentType;
}
