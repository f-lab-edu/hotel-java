package com.hotelJava.room.domain;

import static java.time.LocalDate.now;
import static org.assertj.core.api.Assertions.*;

import com.hotelJava.TestFixture;
import com.hotelJava.common.embeddable.CheckDate;
import com.hotelJava.inventory.domain.Inventory;
import java.util.LinkedList;
import java.util.List;
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
    LinkedList<Inventory> beforeReduce = new LinkedList<>(room.getInventories());
    room.reduceStock(checkDate);

    // then
    assertThat((isReduceQuantitySuccessful(beforeReduce, room.getInventories()))).isTrue();
  }

  private boolean isReduceQuantitySuccessful(List<Inventory> i1, List<Inventory> i2) {
    for (int i = 0; i < i1.size(); i++) {
      long beforeQuantity = i2.get(i).getQuantity();
      long afterQuantity = i2.get(i).getQuantity();
      if (beforeQuantity != afterQuantity) {
        return false;
      }
    }
    return true;
  }

  @Test
  @DisplayName("숙박 기간(체크인, 체크아웃)에 해당하는 재고 중 수량이 0 이하인 재고가 있으면 true 를 반환한다")
  void 객실품절검사_true() {
    // given
    int duration = 10;
    Room room = TestFixture.getRoom(10, 10, now(), duration);
    room.getInventories().get(duration - 1).setQuantity(0);
    CheckDate checkDate = new CheckDate(now(), duration);

    // when, then
    room.isStockOut(checkDate);
    Assertions.assertThat(room.isStockOut(checkDate)).isTrue();
  }

  @Test
  @DisplayName("숙박 기간(체크인, 체크아웃)에 해당하는 재고 중 수량이 0 이하인 재고가 없다면 false 를 반환한다")
  void 객실품절검사_false() {
    // given
    Room room = TestFixture.getRoom(10, 10, now(), 10);
    CheckDate checkDate = new CheckDate(now(), 10);

    // when, then
    Assertions.assertThat(room.isStockOut(checkDate)).isFalse();
  }
}
