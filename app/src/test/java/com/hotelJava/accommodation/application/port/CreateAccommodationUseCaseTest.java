package com.hotelJava.accommodation.application.port;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.hotelJava.accommodation.adapter.persistence.AccommodationRepository;
import com.hotelJava.accommodation.domain.AccommodationType;
import com.hotelJava.accommodation.dto.CreateAccommodationRequest;
import com.hotelJava.accommodation.dto.CreateAccommodationResponse;
import com.hotelJava.common.embeddable.Address;
import com.hotelJava.common.embeddable.CheckTime;
import com.hotelJava.common.error.exception.BadRequestException;
import com.hotelJava.common.util.Base32Util;
import com.hotelJava.picture.domain.PictureInfo;
import com.hotelJava.picture.domain.PictureType;
import com.hotelJava.picture.dto.PictureDto;
import com.hotelJava.room.dto.CreateRoomRequest;
import java.time.LocalTime;
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
public class CreateAccommodationUseCaseTest extends BeforeEachInit {

  @Autowired CreateAccommodationUseCase sut;

  @Autowired AccommodationRepository accommodationRepository;

  @Autowired Base32Util base32Util;

  @DisplayName("숙소와 룸을 등록")
  @Test
  void 숙소_등록() {
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

    PictureInfo roomPictureInfo =
        PictureInfo.builder()
            .name("1번방 사진")
            .originFileName("origin 파일명")
            .saveFileName("save 파일명")
            .extension("확장자")
            .fullPath("전체 경로")
            .fileSize(5000)
            .build();
    PictureInfo roomPictureInfo2 =
        PictureInfo.builder()
            .name("2번방 사진")
            .originFileName("origin 파일명")
            .saveFileName("save 파일명")
            .extension("확장자")
            .fullPath("전체 경로")
            .fileSize(5000)
            .build();

    PictureDto pictureDto =
        PictureDto.builder().pictureInfo(roomPictureInfo).pictureType(PictureType.ROOM).build();
    PictureDto pictureDto2 =
        PictureDto.builder().pictureInfo(roomPictureInfo2).pictureType(PictureType.ROOM).build();

    CreateRoomRequest room =
        CreateRoomRequest.builder()
            .name("자바 게스트하우스 1번방")
            .price(20000)
            .maxOccupancy(6)
            .checkTime(
                CheckTime.builder()
                    .checkInTime(LocalTime.of(12, 0))
                    .checkOutTime(LocalTime.of(12, 0))
                    .build())
            .pictureDtos(List.of(pictureDto))
            .build();
    CreateRoomRequest room2 =
        CreateRoomRequest.builder()
            .name("자바 게스트하우스 2번방")
            .price(3000)
            .maxOccupancy(4)
            .checkTime(
                CheckTime.builder()
                    .checkInTime(LocalTime.of(12, 0))
                    .checkOutTime(LocalTime.of(12, 0))
                    .build())
            .pictureDtos(List.of(pictureDto2))
            .build();

    CreateAccommodationRequest createAccommodationRequest =
        CreateAccommodationRequest.builder()
            .name("test accommodation2")
            .phoneNumber("01012341124")
            .type(AccommodationType.GUESTHOUSE)
            .address(
                Address.builder()
                    .firstLocation("서울")
                    .secondLocation("장안")
                    .fullAddress("서울시 동대문구 장안동 77")
                    .build())
            .pictureDto(
                PictureDto.builder()
                    .pictureInfo(accommodationPictureInfo)
                    .pictureType(PictureType.ACCOMMODATION)
                    .build())
            .createRoomRequests(List.of(room, room2))
            .description("숙소에 대한 설명~")
            .build();

    // when
    CreateAccommodationResponse createAccommodationResponse =
        sut.createAccommodation(createAccommodationRequest);

    // then
    assertThat(createAccommodationResponse.getName())
        .isEqualTo(createAccommodationRequest.getName());
    assertThat(createAccommodationResponse.getAddress())
        .isEqualTo(createAccommodationRequest.getAddress());
    assertThat(createAccommodationResponse.getCreateRoomResponses().get(0).getName())
        .isEqualTo("자바 게스트하우스 1번방");
    assertThat(createAccommodationResponse.getDescription()).isEqualTo("숙소에 대한 설명~");
  }

  @DisplayName("숙소명 중복 확인")
  @Test
  void 숙소명_중복_확인() {
    // given
    CreateAccommodationRequest createAccommodationRequest =
        CreateAccommodationRequest.builder().name("test accommodation").build();

    // when

    // then
    assertThatThrownBy(() -> sut.createAccommodation(createAccommodationRequest))
        .isInstanceOf(BadRequestException.class);
  }
}
