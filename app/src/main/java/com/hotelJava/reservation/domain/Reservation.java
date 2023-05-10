package com.hotelJava.reservation.domain;

import com.hotelJava.common.embeddable.CheckDate;
import com.hotelJava.common.error.ErrorCode;
import com.hotelJava.common.error.exception.BadRequestException;
import com.hotelJava.common.util.BaseTimeEntity;
import com.hotelJava.member.domain.Member;
import com.hotelJava.payment.domain.Payment;
import com.hotelJava.payment.domain.PaymentResult;
import com.hotelJava.room.domain.Room;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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

  private String name;

  private String phone;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "payment_id")
  private Payment payment;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "room_id")
  private Room room;

  public Reservation(Member member, GuestInfo guestInfo) {
    this.member = member;
    this.checkDate = guestInfo.getCheckDate();
    this.name = guestInfo.getName();
    this.numberOfGuests = guestInfo.getNumberOfGuests();
    this.phone = guestInfo.getPhone();
    this.payment = new Payment(room.calcPrice());
  }

  public Reservation confirm(PaymentResult paymentResult) {
    validateReservation();
    payment.approve(paymentResult);
    room.reduceStock(checkDate);
    status = ReservationStatus.RESERVATION_COMPLETED;
    return this;
  }

  private void validateReservation() {
    if (room.isStockOut(checkDate)) {
      log.info("room stock is not enough. payment declined");
      throw new BadRequestException(ErrorCode.PAYMENT_FAIL);
    }

    if (!room.isLowerMaxOccupancy(numberOfGuests)) {
      log.info("guest number is over max occupancy. payment declined");
      throw new BadRequestException(ErrorCode.PAYMENT_FAIL);
    }
  }
}
