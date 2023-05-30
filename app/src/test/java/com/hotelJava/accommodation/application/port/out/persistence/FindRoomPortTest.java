package com.hotelJava.accommodation.application.port.out.persistence;

import static org.assertj.core.api.Assertions.*;

import com.hotelJava.DomainTestFixture;
import com.hotelJava.accommodation.domain.Accommodation;
import com.hotelJava.accommodation.domain.Room;
import com.hotelJava.common.error.exception.BadRequestException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("dev")
@DataJpaTest(
    includeFilters =
        @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Repository.class))
class FindRoomPortTest {

  @Autowired FindRoomPort sut;
  @Autowired SaveAccommodationPort saveAccommodationPort;

  @Test
  @DisplayName("객실 번호로 객실 조회시, 객실 객체를 반환한다.")
  void 객실조회() {
    // given
    Accommodation accommodation = DomainTestFixture.accommodation();
    Room room = DomainTestFixture.room();
    accommodation.addRoom(room);
    saveAccommodationPort.save(accommodation);

    // when & then
    assertThat(sut.findById(room.getId())).isEqualTo(room);
  }

  @Test
  @DisplayName("존재하지 않는 객실 번호로 객실 조회시, 예외가 발생한다.")
  void 객실조회_실패() {
    // when & then
    assertThatThrownBy(() -> sut.findById(1L)).isInstanceOf(BadRequestException.class);
  }
}
