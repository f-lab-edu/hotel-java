package com.hotelJava.payment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hotelJava.payment.domain.PaymentType;
import com.hotelJava.reservation.dto.CreateReservationRequestDto;
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

  private int price;

  @NotNull(message = "결제수단을 선택해주세요.")
  private PaymentType paymentType;

  private int numberOfGuests;

  private String reservationNo;

  private Long accommodationId;

  private Long roomId;

  private CreateReservationRequestDto createReservationRequestDto;
}
