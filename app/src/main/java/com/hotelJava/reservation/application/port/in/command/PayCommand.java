package com.hotelJava.reservation.application.port.in.command;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hotelJava.reservation.domain.PaymentType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PayCommand {

  @JsonProperty("imp_uid")
  private String impUid;

  private PaymentType paymentType;

  private String reservationNo;
}
