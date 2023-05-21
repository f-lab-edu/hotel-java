package com.hotelJava.payment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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

  @JsonProperty("imp_uid")
  private String impUid;

  private int amount;

  private String reservationNo;
}
