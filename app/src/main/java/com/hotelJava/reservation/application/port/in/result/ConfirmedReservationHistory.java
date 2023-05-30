package com.hotelJava.reservation.application.port.in.result;

import com.hotelJava.common.embeddable.CheckDate;
import com.hotelJava.common.embeddable.Money;
import com.hotelJava.reservation.domain.PaymentInfo;
import com.hotelJava.reservation.domain.PaymentStatus;
import com.hotelJava.reservation.domain.ReservationHistory;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class ConfirmedReservationHistory implements ReservationHistory, PaymentInfo {
  // 예약정보
  private String reservationNo;
  private String guestName;
  private String guestPhone;
  private String roomName;
  private CheckDate checkDate;
  private int numberOfGuests;

  // 결제정보
  private PaymentStatus paymentStatus;
  private LocalDateTime paymentDate;
  private Money amount;
}
