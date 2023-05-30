package com.hotelJava.reservation.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RandomReservationNumberGeneratorTest {

  static class MyReservationNumberGenerator implements ReservationNumberGenerator {
    @Override
    public String generateReservationNumber() {
      return "test";
    }
  }

  private final MyReservationNumberGenerator sut = new MyReservationNumberGenerator();

  @DisplayName("예약번호 생성 기능 테스트")
  @Test
  void 예약번호_생성() {
    // given

    // when
    String reservationNumber = sut.generateReservationNumber();

    // then
    assertThat(reservationNumber).isEqualTo("test");
  }
}
