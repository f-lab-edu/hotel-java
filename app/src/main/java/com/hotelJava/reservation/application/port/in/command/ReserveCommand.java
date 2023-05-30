package com.hotelJava.reservation.application.port.in.command;

import com.hotelJava.common.embeddable.CheckDate;
import com.hotelJava.reservation.domain.GuestInfo;
import com.hotelJava.reservation.domain.ReserveType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ReserveCommand implements GuestInfo {

  private ReserveType reserveType;

  @NotBlank(message = "예약자 이름을 입력해주세요.")
  private String guestName;

  @NotBlank(message = "휴대폰 번호의 형식이 맞지 않습니다.")
  @Pattern(regexp = "\\d{3}-\\d{4}-\\d{4}")
  private String guestPhone;

  @Positive(message = "1명 이상의 인원을 입력해주세요.")
  private int numberOfGuests;

  @NotNull(message = "체크인, 체크아웃 시간을 선택해주세요.")
  private CheckDate checkDate;
}
