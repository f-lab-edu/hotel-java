package com.hotelJava.reservation.domain;

import com.hotelJava.common.embeddable.CheckDate;
import com.hotelJava.common.util.BaseTimeEntity;
import com.hotelJava.member.domain.Member;
import com.hotelJava.payment.domain.Payment;
import com.hotelJava.payment.domain.PaymentResult;
import com.hotelJava.payment.domain.PaymentType;import com.hotelJava.room.domain.Room;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.SQLDelete;

@SQLDelete(sql = "UPDATE reservation SET status = 'RESERVATION_CANCEL' WHERE id = ?")
@Slf4j
@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Reservation extends BaseTimeEntity implements GuestInfo {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String reservationNo;

  @Enumerated(EnumType.STRING)
  @Default
  private ReservationStatus status = ReservationStatus.PAYMENT_PENDING;

  @Embedded private CheckDate checkDate;

  private int numberOfGuests;

  private String guestName;

  private String guestPhone;

  private boolean deleted;

  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "payment_id")
  private Payment payment;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "room_id")
  private Room room;

  public Reservation(Member member, Room room, String reservationNo, GuestInfo guestInfo, PaymentType paymentType) {
    this.member = member;
    this.room = room;
    this.reservationNo = reservationNo;
    this.status = ReservationStatus.PAYMENT_PENDING;
    this.checkDate = guestInfo.getCheckDate();
    this.guestName = guestInfo.getGuestName();
    this.guestPhone = guestInfo.getGuestPhone();
    this.numberOfGuests = guestInfo.getNumberOfGuests();
    this.payment = new Payment(room.calcPrice(), paymentType);
  }

  public Reservation confirm(PaymentResult paymentResult) {
    payment.approve(paymentResult);
    status = ReservationStatus.RESERVATION_COMPLETED;
    return this;
  }

  public void consumeStock() {
    room.calcStock(checkDate, -1);
  }

  public void restoreStock() {
    room.calcStock(checkDate, 1);
  }

  public boolean isInvalidReservation() {
    if (room.isNotEnoughStockAtCheckDate(checkDate)) {
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

  public void changeReservationStatus(ReservationStatus reservationStatus) {
    status = reservationStatus;
  }

  public void setRoom(Room room) {
    this.room = room;
  }

  // == 연관관계 편의 메소드 ==//
  public void setMember(Member member) {
    this.member = member;
    member.getReservations().add(this);
  }

  public void setPayment(Payment payment) {
    this.payment = payment;
    payment.setReservation(this);
  }
}
