package com.hotelJava.accommodation.application.port;

import com.hotelJava.accommodation.domain.AccommodationType;
import com.hotelJava.accommodation.dto.UpdateAccommodationRequest;
import com.hotelJava.common.embeddable.Address;
import com.hotelJava.picture.domain.PictureInfo;
import com.hotelJava.picture.domain.PictureType;
import com.hotelJava.picture.dto.PictureDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class UpdateAccommodationUseCaseTest extends BeforeEachInit {

  @Autowired UpdateAccommodationUseCase sut;

  @DisplayName("숙소 정보 수정")
  @Test
  void 숙소_수정() {
    // given
    PictureInfo accommodationPictureInfo =
        PictureInfo.builder()
            .name("자바 게스트 하우스 전체 사진")
            .originFileName("origin 파일명")
            .saveFileName("save 파일명")
            .extension("확장자")
            .fullPath("전체 경로")
            .fileSize(5000)
            .build();

    UpdateAccommodationRequest updateAccommodationRequest =
        UpdateAccommodationRequest.builder()
            .name("숙소 이름 변경")
            .phoneNumber("010-0000-0000")
            .type(AccommodationType.GUESTHOUSE)
            .address(
                Address.builder()
                    .firstLocation("경기도")
                    .secondLocation("수원")
                    .fullAddress("경기도 수원시 팔달구")
                    .build())
            .pictureDto(
                PictureDto.builder()
                    .pictureInfo(accommodationPictureInfo)
                    .pictureType(PictureType.ACCOMMODATION)
                    .build())
            .description("숙소 설명 변경")
            .build();

    // when
    sut.updateAccommodation(accommodation1.getId(), updateAccommodationRequest);

    // then
    assertThat(accommodation1.getName()).isEqualTo("숙소 이름 변경");
    assertThat(accommodation1.getAddress().getSecondLocation()).isEqualTo("수원");
  }
}
