package com.hotelJava.payment.domain;

import com.hotelJava.reservation.domain.Reservation;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
public class Payment {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private int amount;

  @Enumerated(EnumType.STRING)
  private PaymentType paymentType;

  private LocalDateTime paymentDate;

  @Enumerated(EnumType.STRING)
  @Default
  private PaymentStatus status = PaymentStatus.WAITING;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "reservation_id")
  private Reservation reservation;

  public Payment(int amount) {
    this.amount = amount;
  }

  public PaymentStatus approve(PaymentResult paymentResult) {
    if (paymentResult.isPayed(amount)) {
      return status = PaymentStatus.COMPLETE;
    }
    log.info("payment fail");
    return status = PaymentStatus.ERROR;
  }

  public boolean isExpired() {
    return status != PaymentStatus.WAITING;
  }
}
