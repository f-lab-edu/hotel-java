package com.hotelJava.accommodation.application.port.out.persistence;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.hotelJava.DomainTestFixture;
import com.hotelJava.accommodation.domain.Accommodation;
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
class FindAccommodationPortTest {

  @Autowired private FindAccommodationPort sut;
  @Autowired private SaveAccommodationPort saveAccommodationPort;

  @Test
  @DisplayName("숙소 번호로 객실 조회시, 숙소 객체를 반환한다.")
  void 숙소조회() {
    // given
    Accommodation accommodation = saveAccommodationPort.save(DomainTestFixture.accommodation());

    // when & then
    assertThat(sut.findById(accommodation.getId())).isEqualTo(accommodation);
  }

  @Test
  @DisplayName("존재하지 않는 숙소 번호를 조회시, 예외가 발생한다.")
  void 숙소조회_실패() {
    assertThatThrownBy(() -> sut.findById(1L)).isInstanceOf(BadRequestException.class);
  }
}
