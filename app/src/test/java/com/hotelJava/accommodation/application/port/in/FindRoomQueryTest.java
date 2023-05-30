package com.hotelJava.accommodation.application.port.in;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import com.hotelJava.DomainTestFixture;
import com.hotelJava.accommodation.application.port.out.persistence.FindRoomPort;
import com.hotelJava.accommodation.domain.Room;
import com.hotelJava.common.error.exception.BadRequestException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

@SpringBootTest
class FindRoomQueryTest {

  @Autowired private FindRoomQuery sut;
  @SpyBean private FindRoomPort findRoomPort;

  @Test
  @DisplayName("객실 번호로 객실 조회시, 객실 정보를 반환한다.")
  void 객실조회() {
    // given
    Room room = DomainTestFixture.room();
    doReturn(room).when(findRoomPort).findById(anyLong());

    // when & then
    assertThat(sut.findById(1L)).usingRecursiveComparison().isEqualTo(room);
  }

  @Test
  @DisplayName("존재하지 않는 객실 번호로 객실 조회시, 예외가 발생한다.")
  void 객실조회_실패() {
    // given
    doThrow(BadRequestException.class).when(findRoomPort).findById(anyLong());

    // when, then
    assertThatThrownBy(() -> sut.findById(1L)).isInstanceOf(BadRequestException.class);
  }
}
