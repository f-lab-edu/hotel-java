package com.hotelJava.accommodation.application.port.out.persistence;

import static org.assertj.core.api.Assertions.*;

import com.hotelJava.DomainTestFixture;
import com.hotelJava.accommodation.domain.Accommodation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("dev")
@DataJpaTest(includeFilters = @Filter(type = FilterType.ANNOTATION, classes = Repository.class))
class CheckDuplicateAccommodationPortTest {

  @Autowired private CheckDuplicateAccommodationPort sut;
  @Autowired private SaveAccommodationPort saveAccommodationPort;

  @Test
  @DisplayName("이미 존재하는 이름의 숙소를 중복검사시, true를 반환한다")
  void 숙소이름중복검사_true() {
    // given
    Accommodation savedAccommodation =
        saveAccommodationPort.save(DomainTestFixture.accommodation());

    // when & then
    assertThat(sut.isDuplicateByName(savedAccommodation.getName())).isTrue();
  }

  @Test
  @DisplayName("존재하지 않는 이름의 숙소를 중복검사시, false를 반환한다")
  void 숙소이름중복검사_false() {
    // when & then
    assertThat(sut.isDuplicateByName("")).isFalse();
  }
}
