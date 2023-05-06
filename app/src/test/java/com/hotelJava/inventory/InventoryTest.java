package com.hotelJava.inventory;

import static org.assertj.core.api.Assertions.assertThat;

import com.hotelJava.TestFixture;
import com.hotelJava.common.embeddable.CheckDate;
import com.hotelJava.common.error.exception.BadRequestException;
import java.time.LocalDate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class InventoryTest {

  @Test
  @DisplayName("재고가 1보다 많다면 true 를 반환한다")
  void 재고검사() {
    // given
    Inventory inventory = TestFixture.getInventory(TestFixture.getRoom(1), LocalDate.now(), 10);

    // when
    boolean enoughQuantity = inventory.isEnoughQuantity();

    // then
    assertThat(enoughQuantity).isTrue();
  }

  @Test
  @DisplayName("재고가 1보다 적다면 false 를 반환한다")
  void 재고검사_재고부족() {
    // given
    Inventory inventory = TestFixture.getInventory(TestFixture.getRoom(1), LocalDate.now(), 10);

    // when
    boolean enoughQuantity = inventory.isEnoughQuantity();

    // then
    assertThat(enoughQuantity).isTrue();
  }

  @Test
  @DisplayName("숙박 기간에 해당하는 객실상품이고 재고가 1보다 크다면 재고를 1 감소시킨다")
  void 재고감소() {
    // given
    Inventory inventory = TestFixture.getInventory(TestFixture.getRoom(1), LocalDate.now(), 10);
    LocalDate checkin = LocalDate.now();
    LocalDate checkout = checkin.plusDays(1);
    CheckDate checkDate = new CheckDate(checkin, checkout);

    // when
    long expect = inventory.getQuantity() - 1;

    // then
    assertThat(inventory.reduceQuantity(checkDate).getQuantity()).isEqualTo(expect);
  }

  @Test
  @DisplayName("체크인 ~ 체크아웃 기간의 객실재고 중 1보다 작은 날이 하루라도 존재하면 예외가 발생한다")
  void 재고감소_예외발생_재고부족() {
    // given
    Inventory inventory = TestFixture.getInventory(TestFixture.getRoom(1), LocalDate.now(), 0);
    LocalDate checkin = LocalDate.now();
    LocalDate checkout = checkin.plusDays(1);
    CheckDate checkDate = new CheckDate(checkin, checkout);

    // when, then
    Assertions.assertThatThrownBy(() -> inventory.reduceQuantity(checkDate))
        .isInstanceOf(BadRequestException.class);
  }
}
