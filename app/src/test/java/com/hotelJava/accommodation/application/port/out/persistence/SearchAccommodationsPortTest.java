package com.hotelJava.accommodation.application.port.out.persistence;

import static com.hotelJava.accommodation.domain.AccommodationType.*;
import static org.assertj.core.api.Assertions.*;

import com.hotelJava.DomainTestFixture;
import com.hotelJava.accommodation.domain.Accommodation;
import com.hotelJava.common.embeddable.CheckDate;
import com.hotelJava.common.embeddable.Money;
import java.time.LocalDate;
import java.util.List;
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
class SearchAccommodationsPortTest {

  @Autowired SearchAccommodationsPort sut;
  @Autowired SaveAccommodationPort saveAccommodationPort;

  @Test
  @DisplayName("숙소타입을 조건으로 검색하면, 원하는 숙소타입의 숙소만 검색된다")
  void 숙소검색_숙소타입() {

    // given
    List<Accommodation> accommodations = DomainTestFixture.accommodationsWithRooms();
    saveAccommodationPort.saveAll(accommodations);

    // when
    AccommodationSearchCondition condition =
        AccommodationSearchCondition.builder().type(HOTEL_RESORT).build();

    // then
    long expect =
        accommodations.stream()
            .filter(accommodation -> accommodation.getType() == HOTEL_RESORT)
            .count();

    assertThat(sut.search(condition).size()).isEqualTo(expect);
  }

  @Test
  @DisplayName("체크인 날짜를 조건으로 검색하면, 해당 날짜에 예약 가능한 숙소만 검색된다")
  void 숙소검색_체크인날짜_체크인범위정상() {
    // given
    int duration = 5; // 재고 보유 기간
    Accommodation accommodation =
        DomainTestFixture.accommodationWithRooms(LocalDate.now(), duration, 1);
    saveAccommodationPort.save(accommodation);

    // when
    AccommodationSearchCondition searchCondition =
        AccommodationSearchCondition.builder()
            .checkDate(new CheckDate(LocalDate.now(), duration))
            .build();

    // then
    assertThat(sut.search(searchCondition).size()).isEqualTo(1);
  }

  @Test
  @DisplayName("체크인 날짜를 조건으로 검색하면, 해당 날짜에 예약 가능한 숙소만 검색된다")
  void 숙소검색_체크인날짜_체크인범위_재고범위초과() {
    // given
    int duration = 5; // 재고 보유 기간
    Accommodation accommodation =
        DomainTestFixture.accommodationWithRooms(LocalDate.now(), duration, 1);
    saveAccommodationPort.save(accommodation);

    // when
    AccommodationSearchCondition searchCondition =
        AccommodationSearchCondition.builder()
            .checkDate(new CheckDate(LocalDate.now(), duration + 1))
            .build();

    // then
    assertThat(sut.search(searchCondition).size()).isEqualTo(0);
  }

  @Test
  @DisplayName("투숙 인원을 조건으로 검색하면, 투숙 인원을 수용할 수 있는 (최대정원 <= 투숙 인원) 숙소만 검색된다")
  void 숙소검색_최대정원이내() {
    // given
    int occupancy = 10;
    Accommodation accommodation = DomainTestFixture.accommodationWithRooms(occupancy);
    saveAccommodationPort.save(accommodation);

    // when
    AccommodationSearchCondition searchCondition =
        AccommodationSearchCondition.builder().numberOfGuests(occupancy).build();

    // then
    List<Accommodation> result = sut.search(searchCondition);
    assertThat(result.size()).isEqualTo(1);
  }

  @Test
  @DisplayName("투숙 인원을 조건으로 검색하면, 투숙 인원을 수용할 수 있는 (최대정원 <= 투숙 인원) 숙소만 검색된다")
  void 숙소검색_최대정원초과() {
    // given
    int occupancy = 10;
    Accommodation accommodation = DomainTestFixture.accommodationWithRooms(occupancy);
    saveAccommodationPort.save(accommodation);

    // when
    AccommodationSearchCondition searchCondition =
        AccommodationSearchCondition.builder().numberOfGuests(occupancy + 1).build();

    // then
    List<Accommodation> result = sut.search(searchCondition);
    assertThat(result.size()).isEqualTo(0);
  }

  @Test
  @DisplayName("가격을 조건으로 검색하면, 가격 이하 금액의 숙소만 검색된다")
  void 숙소검색_가격이하() {
    // given
    Money[] money = {Money.of(1000), Money.of(20000), Money.of(30000)};
    Accommodation accommodation = DomainTestFixture.accommodationWithRooms(money);
    saveAccommodationPort.save(accommodation);

    // when
    AccommodationSearchCondition searchCondition =
        AccommodationSearchCondition.builder().price(Money.of(20000)).build();

    // then
    List<Accommodation> result = sut.search(searchCondition);
    assertThat(result.size()).isEqualTo(1);
  }

  @Test
  @DisplayName("가격을 조건으로 검색하면, 가격 이하 금액의 숙소만 검색된다")
  void 숙소검색_가격초과() {
    // given
    Money[] money = {Money.of(50000), Money.of(60000), Money.of(70000)};
    Accommodation accommodation = DomainTestFixture.accommodationWithRooms(money);
    saveAccommodationPort.save(accommodation);

    // when
    AccommodationSearchCondition searchCondition =
        AccommodationSearchCondition.builder().price(Money.of(40000)).build();

    // then
    List<Accommodation> result = sut.search(searchCondition);
    assertThat(result.size()).isEqualTo(0);
  }
}
