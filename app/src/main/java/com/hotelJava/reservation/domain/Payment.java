package com.hotelJava.reservation.domain;

import com.hotelJava.common.embeddable.Money;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Payment implements PaymentInfo {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Money amount;

  @Enumerated(EnumType.STRING)
  private PaymentType paymentType;

  private LocalDateTime paymentDate;

  @Enumerated(EnumType.STRING)
  @Default
  private PaymentStatus paymentStatus = PaymentStatus.WAITING;

  @OneToOne(fetch = FetchType.LAZY, mappedBy = "payment")
  private Reservation reservation;

  public Payment(Money money) {
    this.amount = money;
    this.paymentStatus = PaymentStatus.WAITING;
  }

  public boolean isExpired() {
    return paymentStatus != PaymentStatus.WAITING;
  }

  public void setReservation(Reservation reservation) {
    this.reservation = reservation;
  }

  public Payment approve() {
    paymentDate = LocalDateTime.now();
    paymentStatus = PaymentStatus.COMPLETE;
    return this;
  }
}
