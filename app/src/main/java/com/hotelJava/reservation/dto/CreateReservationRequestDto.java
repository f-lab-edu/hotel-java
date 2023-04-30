package com.hotelJava.reservation.dto;

import com.hotelJava.common.embeddable.CheckDate;
import com.hotelJava.payment.dto.CreatePaymentRequestDto;
import com.hotelJava.reservation.domain.ReservationCommand;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CreateReservationRequestDto {

  private ReservationCommand reservationCommand;

  @NotBlank(message = "예약자 이름을 입력해주세요.")
  private String name;

  @NotBlank(message = "휴대폰 번호의 형식이 맞지 않습니다.")
  @Pattern(regexp = "\\d{3}-\\d{4}-\\d{4}")
  private String phoneNumber;

  @NotNull(message = "체크인, 체크아웃 시간을 선택해주세요.")
  @Future
  private CheckDate checkDate;

  private CreatePaymentRequestDto createPaymentRequestDto;
}
