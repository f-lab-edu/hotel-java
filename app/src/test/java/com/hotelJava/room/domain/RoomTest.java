package com.hotelJava.room.domain;

import static java.time.LocalDate.now;
import static org.assertj.core.api.Assertions.*;

import com.hotelJava.TestFixture;
import com.hotelJava.common.embeddable.CheckDate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RoomTest {

  @Test
  @DisplayName("숙박 기간(체크인, 체크아웃)에 해당하는 재고를 1씩 감소시킨다")
  void 객실재고감소() {
    // given
    Room room = TestFixture.getRoom(10, 10, now(), 10);
    CheckDate checkDate = new CheckDate(now(), 1);

    // when
    room.calcStock(checkDate, -1);

    // then
    assertThat(
            room.getstocks().stream()
                .filter(i -> checkDate.matches(i.getDate()))
                .allMatch(i -> i.getQuantity() == 9))
        .isTrue();
  }

  @Test
  @DisplayName("숙박 기간(체크인, 체크아웃)에 해당하는 재고 중 수량이 0 이하인 재고가 있으면 true 를 반환한다")
  void 객실품절검사_true() {
    // given
    int duration = 10;
    Room room = TestFixture.getRoom(10, 10, now(), duration);
    room.getstocks().get(duration - 1).setQuantity(0);
    CheckDate checkDate = new CheckDate(now(), duration);

    // when, then
    room.isNotEnoughStockAtCheckDate(checkDate);
    Assertions.assertThat(room.isNotEnoughStockAtCheckDate(checkDate)).isTrue();
  }

  @Test
  @DisplayName("숙박 기간(체크인, 체크아웃)에 해당하는 재고 중 수량이 0 이하인 재고가 없다면 false 를 반환한다")
  void 객실품절검사_false() {
    // given
    Room room = TestFixture.getRoom(10, 10, now(), 10);
    CheckDate checkDate = new CheckDate(now(), 10);

    // when, then
    Assertions.assertThat(room.isNotEnoughStockAtCheckDate(checkDate)).isFalse();
  }
}
