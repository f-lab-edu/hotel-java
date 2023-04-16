package com.hotelJava.accommodation.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.hotelJava.accommodation.domain.Accommodation;
import com.hotelJava.accommodation.domain.AccommodationType;
import com.hotelJava.accommodation.dto.CreateAccommodationRequestDto;
import com.hotelJava.accommodation.dto.CreateAccommodationResponseDto;
import com.hotelJava.accommodation.dto.FindAccommodationResponseDto;
import com.hotelJava.accommodation.dto.UpdateAccommodationRequestDto;
import com.hotelJava.accommodation.picture.domain.Picture;
import com.hotelJava.accommodation.picture.domain.PictureInfo;
import com.hotelJava.accommodation.picture.domain.PictureType;
import com.hotelJava.accommodation.picture.dto.PictureDto;
import com.hotelJava.accommodation.repository.AccommodationRepository;
import com.hotelJava.common.embeddable.Address;
import com.hotelJava.common.embeddable.CheckDate;
import com.hotelJava.common.embeddable.CheckTime;
import com.hotelJava.common.error.exception.BadRequestException;
import com.hotelJava.payment.domain.PaymentType;
import com.hotelJava.reservation.domain.Reservation;
import com.hotelJava.reservation.domain.ReservationStatus;
import com.hotelJava.room.domain.Room;
import com.hotelJava.room.domain.RoomAvailability;
import com.hotelJava.room.dto.CreateRoomRequestDto;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class AccommodationServiceTest {

  @Autowired AccommodationService accommodationService;

  @Autowired AccommodationRepository accommodationRepository;

  private Accommodation accommodation1, accommodation2;

  @BeforeEach
  public void init() {
    // 숙소/룸 사진 정보
    PictureInfo pictureInfo =
        PictureInfo.builder()
            .name("picture name")
            .originFileName("originFileName")
            .saveFileName("saveFileName")
            .extension("extension")
            .fullPath("fullPath")
            .fileSize(100)
            .build();

    // 숙소 사진
    Picture accommodationPicture =
        Picture.builder().pictureInfo(pictureInfo).pictureType(PictureType.ACCOMMODATION).build();

    // 룸 사진
    Picture roomPicture1 =
        Picture.builder().pictureInfo(pictureInfo).pictureType(PictureType.ROOM).build();
    Picture roomPicture2 =
        Picture.builder().pictureInfo(pictureInfo).pictureType(PictureType.ROOM).build();

    // 예약 시 사용자의 원하는 숙박일
    CheckDate checkDate =
        CheckDate.builder()
            .checkInDate(LocalDate.of(2023, 4, 10))
            .checkOutDate(LocalDate.of(2023, 4, 12))
            .build();

    // 룸의 체크인, 체크아웃 시간
    CheckTime checkTime =
        CheckTime.builder()
            .checkInTime(LocalTime.of(14, 0))
            .checkOutTime(LocalTime.of(11, 0))
            .build();

    // 룸
    Room room1 =
        Room.builder().name("1번 룸").price(1000).maxOccupancy(2).checkTime(checkTime).build();
    Room room2 =
        Room.builder().name("2번 룸").price(2000).maxOccupancy(2).checkTime(checkTime).build();
    Room room3 =
        Room.builder().name("3번 룸룸룸").price(30000).maxOccupancy(2).checkTime(checkTime).build();

    // 예약
    Reservation reservation1 =
        Reservation.builder()
            .name("예약자 성명1")
            .reservationNo("000000002023")
            .accommodationName("예약 숙소 명")
            .roomName("예약 룸 명")
            .checkDate(checkDate)
            .numberOfGuests(2)
            .paymentType(PaymentType.CARD)
            .phoneNumber("010-1234-5678")
            .status(ReservationStatus.RESERVATION_CANCEL)
            .build();
    Reservation reservation2 =
        Reservation.builder()
            .name("예약자 성명2")
            .reservationNo("000000002023")
            .accommodationName("예약 숙소 명")
            .roomName("예약 룸 명")
            .checkDate(checkDate)
            .numberOfGuests(2)
            .paymentType(PaymentType.CARD)
            .phoneNumber("010-1234-5678")
            .status(ReservationStatus.RESERVATION_CANCEL)
            .build();

    // 룸 날짜별 예약 상태
    RoomAvailability roomAvailability1 =
        RoomAvailability.builder()
            .reservationDate(LocalDate.of(2023, 5, 1))
            .status(ReservationStatus.RESERVATION_COMPLETED)
            .build();
    RoomAvailability roomAvailability2 =
        RoomAvailability.builder()
            .reservationDate(LocalDate.of(2023, 5, 2))
            .status(ReservationStatus.RESERVATION_COMPLETED)
            .build();

    // 지역 정보
    Address address =
        Address.builder()
            .firstLocation("서울")
            .secondLocation("강남")
            .fullAddress("서울시 강남구 역삼동 123-456")
            .build();

    // 숙소
    accommodation1 =
        Accommodation.builder()
            .name("test accommodation")
            .type(AccommodationType.HOTEL_RESORT)
            .rating(4.5)
            .phoneNumber("010-1234-5678")
            .address(address)
            .description("숙소 조회 테스트")
            .status(ReservationStatus.RESERVATION_AVAILABLE)
            .build();
    accommodation2 =
        Accommodation.builder()
            .name("숙소 이름이 숙소 이름입니다")
            .type(AccommodationType.HOTEL_RESORT)
            .rating(4.5)
            .phoneNumber("010-1234-5678")
            .address(address)
            .description("숙소 조회 테스트")
            .status(ReservationStatus.RESERVATION_AVAILABLE)
            .build();

    room1.addRoomAvailability(roomAvailability1);
    room1.addRoomAvailability(roomAvailability2);
    room1.addReservation(reservation1);
    room1.addPicture(roomPicture1);
    room1.addPicture(roomPicture2);

    room3.addRoomAvailability(roomAvailability1);
    room3.addRoomAvailability(roomAvailability2);
    room3.addReservation(reservation2);

    accommodation1.createAccommodation(List.of(room1, room2), accommodationPicture);
    accommodation2.createAccommodation(List.of(room3), accommodationPicture);

    accommodationRepository.save(accommodation1);
    accommodationRepository.save(accommodation2);
  }

  @DisplayName("숙소타입, 지역, 이름, 체크인 ~ 체크아웃 날짜, 게스트 수를 이용하여 숙소를 조회")
  @Test
  void 숙소_조회() {
    // given
    AccommodationType type = AccommodationType.HOTEL_RESORT;
    String firstLocation = "서울";
    String secondLocation = "강남";
    String name = "";
    LocalDate checkInDate = LocalDate.of(2023, 5, 6);
    LocalDate checkOutDate = LocalDate.of(2023, 5, 7);

    // when
    List<FindAccommodationResponseDto> accommodations =
        accommodationService.findAccommodations(
            type, firstLocation, secondLocation, name, checkInDate, checkOutDate, 2);

    // then
    assertThat(accommodations.size()).isEqualTo(2);
    assertThat(accommodations.get(0).getName()).isEqualTo("test accommodation");
    assertThat(accommodations.get(0).getMinimumRoomPrice()).isEqualTo(1000);
  }

  @DisplayName("숙소명 중복 확인")
  @Test
  void 숙소명_중복_확인() {
    // given
    CreateAccommodationRequestDto createAccommodationRequestDto =
        CreateAccommodationRequestDto.builder().name("test accommodation").build();

    // when

    // then
    assertThatThrownBy(() -> accommodationService.saveAccommodation(createAccommodationRequestDto))
        .isInstanceOf(BadRequestException.class);
  }

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

    CreateRoomRequestDto room =
        CreateRoomRequestDto.builder()
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
    CreateRoomRequestDto room2 =
        CreateRoomRequestDto.builder()
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

    CreateAccommodationRequestDto createAccommodationRequestDto =
        CreateAccommodationRequestDto.builder()
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
            .createRoomRequestDtos(List.of(room, room2))
            .description("숙소에 대한 설명~")
            .build();

    // when
    CreateAccommodationResponseDto createAccommodationResponseDto =
        accommodationService.saveAccommodation(createAccommodationRequestDto);

    // then
    assertThat(createAccommodationResponseDto.getName())
        .isEqualTo(createAccommodationRequestDto.getName());
    assertThat(createAccommodationResponseDto.getAddress())
        .isEqualTo(createAccommodationRequestDto.getAddress());
    assertThat(createAccommodationResponseDto.getCreateRoomResponseDtos().get(0).getName())
        .isEqualTo("자바 게스트하우스 1번방");
    assertThat(createAccommodationResponseDto.getDescription()).isEqualTo("숙소에 대한 설명~");
  }

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

    UpdateAccommodationRequestDto updateAccommodationRequestDto =
        UpdateAccommodationRequestDto.builder()
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
    accommodationService.updateAccommodation(accommodation1.getId(), updateAccommodationRequestDto);

    // then
    assertThat(accommodation1.getName()).isEqualTo("숙소 이름 변경");
    assertThat(accommodation1.getAddress().getSecondLocation()).isEqualTo("수원");
  }
}
