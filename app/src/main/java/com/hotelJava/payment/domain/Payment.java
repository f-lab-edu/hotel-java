package com.hotelJava.payment.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Payment {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private int amount;
  private PaymentType paymentType;
  private LocalDateTime paymentDate;

//  @Default private PaymentStatus status = PaymentStatus.WAITING;

  public Payment(int amount) {
    this.amount = amount;
  }

  //  public void changeStatus(PaymentStatus status) {
  //    this.status = status;
  //  }

  public boolean isPaymentSuccess() {
    // TODO: 포트원 API 로부터 정상 결제됐는지 확인
    // if (정상처리) this.status = PaymentStatus.COMPLETE;
    //    return status == PaymentStatus.COMPLETE;
    return true;
  }
}
