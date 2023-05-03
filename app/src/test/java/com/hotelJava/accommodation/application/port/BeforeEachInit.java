package com.hotelJava.accommodation.application.port;

import com.hotelJava.accommodation.adapter.persistence.AccommodationRepository;import com.hotelJava.accommodation.domain.Accommodation;
import com.hotelJava.accommodation.domain.AccommodationType;
import com.hotelJava.common.embeddable.Address;
import com.hotelJava.common.embeddable.CheckDate;
import com.hotelJava.common.embeddable.CheckTime;
import com.hotelJava.payment.domain.PaymentType;
import com.hotelJava.picture.domain.Picture;
import com.hotelJava.picture.domain.PictureInfo;
import com.hotelJava.picture.domain.PictureType;
import com.hotelJava.reservation.domain.Reservation;
import com.hotelJava.reservation.domain.ReservationStatus;
import com.hotelJava.room.domain.Room;
import com.hotelJava.room.domain.RoomAvailability;
import org.junit.jupiter.api.BeforeEach;import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class BeforeEachInit {

  @Autowired AccommodationRepository accommodationRepository;

  public Accommodation accommodation1, accommodation2;

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
}
