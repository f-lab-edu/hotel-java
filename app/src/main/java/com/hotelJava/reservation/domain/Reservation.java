package com.hotelJava.reservation.domain;

import com.hotelJava.common.embeddable.CheckDate;
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

  private String guestName;

  private String guestPhone;

  @OneToOne(fetch = FetchType.LAZY, mappedBy = "reservation", cascade = CascadeType.ALL)
  private Payment payment;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "room_id")
  private Room room;

  public Reservation(Member member, Room room, String reservationNo, GuestInfo guestInfo) {
    this.member = member;
    this.room = room;
    this.reservationNo = reservationNo;
    this.checkDate = guestInfo.getCheckDate();
    this.guestName = guestInfo.getGuestName();
    this.guestPhone = guestInfo.getGuestPhone();
    this.numberOfGuests = guestInfo.getNumberOfGuests();
    this.payment = new Payment(room.calcPrice());
  }

  public Reservation confirm(PaymentResult paymentResult) {
    payment.approve(paymentResult);
    status = ReservationStatus.RESERVATION_COMPLETED;
    return this;
  }

  public void consumeInventory() {
    room.calcInventory(checkDate, -1);
  }

  public void restoreInventory() {
    room.calcInventory(checkDate, 1);
  }

  public boolean isInvalidReservation() {
    if (room.isNotEnoughInventoryAtCheckDate(checkDate)) {
      log.info("room stock is not enough. payment declined");
      return true;
    }

    if (room.isOverMaxOccupancy(numberOfGuests)) {
      log.info("guest number is over max occupancy. payment declined");
      return true;
    }

    return false;
  }

  public boolean isExpiredPayment() {
    if (payment.isExpired()) {
      log.info("payment time out. payment declined");
      return true;
    }
    return false;
  }
}
