package com.hotelJava.accommodation.application.port.in;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.hotelJava.DomainTestFixture;
import com.hotelJava.accommodation.application.port.in.result.SearchAccommodationResult;
import com.hotelJava.accommodation.application.port.out.persistence.SearchAccommodationsPort;
import com.hotelJava.accommodation.domain.Accommodation;
import com.hotelJava.accommodation.domain.AccommodationType;
import com.hotelJava.common.embeddable.Money;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class SearchAccommodationQueryTest {

  @Autowired SearchAccommodationQuery sut;
  @SpyBean SearchAccommodationsPort searchAccommodationsPort;

  @DisplayName("숙소타입, 지역, 이름, 체크인 ~ 체크아웃 날짜, 게스트 수를 조건으로 숙소를 조회하면 부합하는 객실 중 가장 저렴한 객실을 찾아낸다.")
  @Test
  void 숙소조회() {
    // given

    // 10001-50000 가격의 객실 10개가 있는 숙소
    Accommodation accommodation =
        DomainTestFixture.accommodationWithRooms(LocalDate.now(), 5, 10, 10001, 50000);

    // 최저가 객실
    Money minMoney = Money.of(10000);
    accommodation.addRoom(DomainTestFixture.room(minMoney));

    doReturn(List.of(accommodation))
        .when(searchAccommodationsPort)
        .search(
            any(AccommodationType.class),
            anyString(),
            anyString(),
            anyString(),
            any(LocalDate.class),
            any(LocalDate.class),
            anyInt());

    // when
    List<SearchAccommodationResult> searchAccommodationResults =
        sut.search(
            accommodation.getType(),
            accommodation.getAddress().getFirstLocation(),
            accommodation.getAddress().getSecondLocation(),
            accommodation.getName(),
            LocalDate.now(),
            LocalDate.now().plusDays(1),
            1);

    // then
    assertThat(searchAccommodationResults.size()).isEqualTo(1);
    assertThat(searchAccommodationResults.get(0).getMinimumRoomPrice())
        .isEqualTo(minMoney.longValue());
  }
}
