package com.hotelJava.reservation.domain;

import com.hotelJava.common.embeddable.CheckDate;
import com.hotelJava.common.error.ErrorCode;
import com.hotelJava.common.error.exception.BadRequestException;
import com.hotelJava.common.util.BaseTimeEntity;
import com.hotelJava.member.domain.Member;
import com.hotelJava.payment.domain.Payment;
import com.hotelJava.room.domain.Room;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Reservation extends BaseTimeEntity implements GuestInfo {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String reservationNo;

  @Builder.Default
  @Enumerated(EnumType.STRING)
  private ReservationStatus status = ReservationStatus.PAYMENT_PENDING;

  @Embedded private CheckDate checkDate;

  private int numberOfGuests;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "payment_id")
  private Payment payment;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "room_id")
  private Room room;

  public Reservation(GuestInfo info) {
    this.member = info.getMember();
    this.checkDate = info.getCheckDate();
    this.numberOfGuests = info.getNumberOfGuests();
    this.payment = new Payment(room.calcPrice());
  }

  public Reservation confirm() {
    validateReservation();
    if (!payment.isPaymentSuccess()) {
      throw new BadRequestException(ErrorCode.PAYMENT_FAIL);
    }
    room.reduceStock(checkDate);
    // TODO: 결제승인
    status = ReservationStatus.RESERVATION_COMPLETED;
    // payment.approve();
    return this;
  }

  public void validateReservation() {
    if (!room.isAvailableReservation(numberOfGuests, checkDate)) {
      // TODO: 결제승인거절
      // payment.decline();
      log.info("payment declined");
      throw new BadRequestException(ErrorCode.PAYMENT_FAIL);
    }
  }
}
