package com.hotelJava.accommodation;

import com.hotelJava.accommodation.domain.Accommodation;
import com.hotelJava.accommodation.domain.AccommodationPicture;
import com.hotelJava.accommodation.domain.AccommodationType;
import com.hotelJava.accommodation.domain.Address;
import com.hotelJava.accommodation.service.AccommodationService;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class AccommodationServiceTest {

    @Autowired
    AccommodationService accommodationService;

    @Autowired
    EntityManager em;


    @BeforeEach
    public void init() {
        Accommodation accommodation = Accommodation.builder()
                .name("테스트 모텔")
                .type(AccommodationType.MOTEL)
                .rating(5.0)
                .phoneNumber("01011241124")
                .address(Address.builder()
                        .firstLocation("서울")
                        .secoundLocation("강남/역삼/삼성/논현")
                        .address("서울시 강남구 역삼동 123-456")
                        .build())
                .accommodationPicture(AccommodationPicture.builder()
                        .title("테스트 모텔 타이틀")
                        .originFileName("testMotel.png")
                        .saveFileName("testMotel")
                        .extension(".png")
                        .fileSize(1024)
                        .fullPath("C:\\\\")
                        .build())
                .description("전 객실 넷플릭스")
                .build();

        em.persist(accommodation);
    }

    @DisplayName("숙소 타입과 지역을 이용하여 숙소를 조회한다.")
    @Test
    void 숙소_조회() {
        // given
        Address address = Address.builder()
                .firstLocation("서울")
                .secoundLocation("장한평/군자")
                .build();

        // when
        List<Accommodation> accommodations = accommodationService.findByTypeAndAddressFirstLocation(AccommodationType.MOTEL, address);

        // then
        assertThat(accommodations.get(0).getName()).isEqualTo("테스트 모텔");
        assertThat(accommodations.size()).isEqualTo(1);
    }
}
