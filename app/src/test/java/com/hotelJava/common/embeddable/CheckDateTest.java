package com.hotelJava.common.embeddable;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CheckDateTest {

  @Test
  @DisplayName("주어진 날짜가 숙박 기간 사이의 날짜라면 true 를 반환한다")
  void 숙박기간_날짜비교_true() {
    // given
    CheckDate checkDate = new CheckDate(LocalDate.of(2023, 5, 10), 2);

    // then
    assertThat(checkDate.matches(LocalDate.of(2023, 5, 10))).isTrue();
    assertThat(checkDate.matches(LocalDate.of(2023, 5, 11))).isTrue();
  }

  @Test
  @DisplayName("주어진 날짜가 숙박 기간 사이의 날짜가 아니라면 false 를 반환한다")
  void 숙박기간_날짜비교_false() {
    // given
    CheckDate checkDate = new CheckDate(LocalDate.of(2023, 5, 10), 2);

    // then
    assertThat(checkDate.matches(LocalDate.of(2023, 5, 12))).isFalse();
  }
}
