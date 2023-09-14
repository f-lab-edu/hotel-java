package com.hotelJava.accommodation.application.port;

import static org.assertj.core.api.Assertions.assertThat;

import com.hotelJava.accommodation.adapter.persistence.AccommodationRepository;
import com.hotelJava.accommodation.domain.AccommodationType;
import com.hotelJava.accommodation.dto.FindAccommodationResponse;
import com.hotelJava.common.util.Base32Util;
import com.hotelJava.member.domain.Role;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class FindAccommodationQueryTest {

  @Autowired FindAccommodationQuery sut;

  @Autowired AccommodationRepository accommodationRepository;

  @Autowired Base32Util base32Util;

  @DisplayName("숙소타입, 지역, 이름, 체크인 ~ 체크아웃 날짜, 게스트 수를 이용하여 숙소를 조회")
  @Test
  void 숙소_조회() {
    // given
    AccommodationType type = AccommodationType.HOTEL_RESORT;
    String firstLocation = "서울";
    String secondLocation = "강남";
    String name = "";
    LocalDate checkInDate = LocalDate.now();
    LocalDate checkOutDate = LocalDate.now().plusDays(1);

    // when
    List<FindAccommodationResponse> accommodations =
        sut.findAccommodations(
            type, firstLocation, secondLocation, name, checkInDate, checkOutDate, 2, Role.USER);

    // then
    assertThat(accommodations.size()).isEqualTo(1);
    assertThat(accommodations.get(0).getName()).isEqualTo("data.sql 호텔");
    assertThat(accommodations.get(0).getMinimumRoomPrice()).isEqualTo(10000);
  }
}
