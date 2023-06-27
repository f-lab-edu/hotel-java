package com.hotelJava.payment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CreatePaymentRequest {

  @JsonProperty("imp_uid")
  private String impUid;

  private int amount;

  private String reservationNo;
}
