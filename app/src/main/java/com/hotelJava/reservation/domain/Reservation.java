package com.hotelJava.reservation.domain;

import com.hotelJava.accommodation.domain.Room;
import com.hotelJava.common.embeddable.CheckDate;
import com.hotelJava.common.util.BaseTimeEntity;
import com.hotelJava.member.domain.Member;
import com.hotelJava.reservation.application.port.in.command.ReserveCommand;
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
  private ReserveType reserveType;

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

  public Reservation(
      Member member, Room room, String reservationNo, ReserveCommand reserveCommand) {
    this.member = member;
    this.room = room;
    this.reservationNo = reservationNo;
    this.status = ReservationStatus.PAYMENT_PENDING;
    this.checkDate = reserveCommand.getCheckDate();
    this.guestName = reserveCommand.getGuestName();
    this.guestPhone = reserveCommand.getGuestPhone();
    this.numberOfGuests = reserveCommand.getNumberOfGuests();
    this.reserveType = reserveCommand.getReserveType();
    this.payment = new Payment(room.getPrice());
  }

  public Reservation confirm() {
    payment.approve();
    status = ReservationStatus.RESERVATION_COMPLETED;
    return this;
  }

  public void consumeStock() {
    room.calcStock(checkDate, -1);
  }

  public void restoreStock() {
    room.calcStock(checkDate, 1);
  }

  public boolean isAlreadyConfirmed() {
    return status == ReservationStatus.RESERVATION_COMPLETED;
  }

  public boolean isExpiredPayment() {
    return payment.isExpired();
  }

  public boolean isNotEnoughStockAtCheckDate() {
    return room.isNotEnoughStockAtCheckDate(checkDate);
  }

  public boolean isOverMaxOccupancy() {
    return room.isOverMaxOccupancy(numberOfGuests);
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
