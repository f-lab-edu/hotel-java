package com.hotelJava.accommodation.domain;

import static org.assertj.core.api.Assertions.*;

import com.hotelJava.DomainTestFixture;
import com.hotelJava.common.embeddable.Money;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AccommodationTest {

  // 객실 가격 = [10000, ... ,90000, 100000]
  // 재고 관리 기간 = 10일
  private Accommodation given() {
    Accommodation accommodation = DomainTestFixture.accommodation();
    for (int i = 1; i <= 10; i++) {
      accommodation.addRoom(DomainTestFixture.room(LocalDate.now(), 10, Money.of(i * 10000L)));
    }
    return accommodation;
  }

  @Test
  @DisplayName("체크인 기간에 해당하고 재고가 충분한 객실 중 가장 저렴한 객실을 반환한다.")
  void 최저가객실조회() {
    // when
    Room minPriceRoom = given().getMinPriceRoom().orElseThrow();

    // then
    assertThat(minPriceRoom.getPrice().longValue()).isEqualTo(10000L);
  }

  @Test
  @DisplayName("가장 저렴하더라도 재고가 부족하다면 해당 객실은 반환되지 않는다.")
  void 최저가객실조회_재고부족() {
    // given
    Accommodation accommodation = given();

    // 최저가 객실이지만 재고가 부족
    long minPrice = 9000L;
    Room room = DomainTestFixture.room(LocalDate.now(), 10, 0, Money.of(minPrice));
    accommodation.addRoom(room);

    // when
    Room minPriceRoom = given().getMinPriceRoom().orElseThrow();

    // then
    assertThat(minPriceRoom.getPrice().longValue()).isNotSameAs(minPrice);
  }
}
