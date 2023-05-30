package com.hotelJava.accommodation.domain;

import static java.time.LocalDate.now;
import static org.assertj.core.api.Assertions.assertThat;

import com.hotelJava.DomainTestFixture;
import com.hotelJava.common.embeddable.CheckDate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RoomTest {
  @Test
  @DisplayName("숙박 기간(체크인, 체크아웃)에 해당하는 재고를 1씩 감소시킨다")
  void 객실재고감소() {
    // given
    Room room = DomainTestFixture.room(now(), 10, 1);
    CheckDate checkDate = new CheckDate(now(), 1);

    // when
    room.calcStock(checkDate, -1);

    // then
    assertThat(
            room.getStocks().values().stream()
                .filter(i -> checkDate.matches(i.getDate()))
                .allMatch(i -> i.getQuantity() == 0))
        .isTrue();
  }

  @Test
  @DisplayName("숙박 기간(체크인, 체크아웃)에 해당하는 재고 중 수량이 0 이하인 재고가 있으면 true 를 반환한다")
  void 객실품절검사() {
    // given
    int duration = 10;
    Room room = DomainTestFixture.room(now(), duration, 1);
    room.getStocks().get(now()).setQuantity(0);

    // when
    CheckDate checkDate = new CheckDate(now(), duration);

    // then
    Assertions.assertThat(room.isNotEnoughStockAtCheckDate(checkDate)).isTrue();
  }

  @Test
  @DisplayName("숙박 기간(체크인, 체크아웃)에 해당하는 재고 데이터가 데이터베이스상에 존재하지 않는다면 true 를 반환한다")
  void 객실품절검사_재고데이터가없는경우() {
    // given
    int duration = 20;
    Room room = DomainTestFixture.room(now(), duration, 1);

    // when
    CheckDate checkDate = new CheckDate(now(), duration + 1);

    // then
    Assertions.assertThat(room.isNotEnoughStockAtCheckDate(checkDate)).isTrue();
  }

  @Test
  @DisplayName("숙박 기간(체크인, 체크아웃)에 해당하는 재고 중 수량이 0 이하인 재고가 없다면 false 를 반환한다")
  void 객실품절검사_재고가충분한경우() {
    // given
    Room room = DomainTestFixture.room(now(), 10, 1);

    // when
    CheckDate checkDate = new CheckDate(now(), 10);

    // then
    Assertions.assertThat(room.isNotEnoughStockAtCheckDate(checkDate)).isFalse();
  }
}
