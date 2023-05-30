package com.hotelJava.accommodation.application.port.out.persistence;

import static org.assertj.core.api.Assertions.*;

import com.hotelJava.DomainTestFixture;
import com.hotelJava.accommodation.domain.Accommodation;
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
class SaveAccommodationPortTest {

  @Autowired private SaveAccommodationPort sut;
  @Autowired private FindAccommodationPort findAccommodationPort;

  @Test
  @DisplayName("숙소 등록 모델이 주어졌을 때, 숙소를 등록할 수 있다.")
  void 숙소등록() {
    // given
    Accommodation accommodation = DomainTestFixture.accommodation();

    // when
    sut.save(accommodation);

    // then
    assertThat(findAccommodationPort.findById(accommodation.getId())).isEqualTo(accommodation);
  }
}
