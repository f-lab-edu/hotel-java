package com.hotelJava;

import com.github.javafaker.Faker;
import com.hotelJava.accommodation.domain.Accommodation;
import com.hotelJava.accommodation.domain.AccommodationType;
import com.hotelJava.common.embeddable.Address;
import com.hotelJava.common.embeddable.CheckDate;
import com.hotelJava.member.application.port.in.command.ChangeProfileCommand;
import com.hotelJava.member.application.port.in.command.MemberSignUpCommand;
import com.hotelJava.member.domain.Member;
import com.hotelJava.payment.dto.CreatePaymentRequestDto;
import com.hotelJava.picture.domain.Picture;
import com.hotelJava.picture.domain.PictureInfo;
import com.hotelJava.reservation.domain.Reservation;
import com.hotelJava.reservation.domain.ReservationCommand;
import com.hotelJava.reservation.domain.ReservationStatus;
import com.hotelJava.reservation.dto.CreateReservationRequestDto;
import com.hotelJava.room.domain.Room;
import com.hotelJava.stock.domain.Stock;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class TestFixture {

  private static final Faker faker = Faker.instance();

  /** dto test fixture * */
  public static MemberSignUpCommand getMemberSignUpCommand() {
    return MemberSignUpCommand.builder()
        .email(faker.internet().emailAddress())
        .rawPassword(faker.internet().password())
        .name(faker.name().name())
        .phone(faker.phoneNumber().phoneNumber())
        .build();
  }

  public static ChangeProfileCommand getChangeProfileCommand() {
    return ChangeProfileCommand.builder()
        .name(faker.name().name())
        .phone(faker.phoneNumber().phoneNumber())
        .build();
  }

  public static CreateReservationRequestDto getCreateReservationRequestDto(CheckDate checkDate) {
    return CreateReservationRequestDto.builder()
        .reservationCommand(faker.options().option(ReservationCommand.values()))
        .guestName(faker.name().name())
        .guestPhone(faker.phoneNumber().phoneNumber())
        .numberOfGuests(faker.number().randomDigit())
        .checkDate(checkDate)
        .build();
  }

  public static CreatePaymentRequestDto getCreatePaymentRequestDto() {
    return CreatePaymentRequestDto.builder()
        .impUid(faker.random().toString())
        .amount(faker.number().numberBetween(50000, 100000))
        .reservationNo(faker.random().toString())
        .build();
  }

  /** domain test fixture * */
  public static Member getMember() {
    return Member.builder()
        .email(faker.internet().emailAddress())
        .name(faker.name().name())
        .password(faker.internet().password())
        .phone(faker.phoneNumber().phoneNumber())
        .build();
  }

  public static Address getAddress() {
    return Address.builder()
        .firstLocation(faker.address().streetName())
        .secondLocation(faker.address().cityName())
        .fullAddress(faker.address().buildingNumber())
        .build();
  }

  public static PictureInfo getPictureInfo() {
    return PictureInfo.builder()
        .name(faker.file().fileName())
        .saveFileName(faker.file().fileName())
        .originFileName(faker.file().fileName())
        .fileSize(faker.number().randomNumber())
        .fullPath(faker.letterify("????/????/????"))
        .extension(faker.file().extension())
        .build();
  }

  public static Picture getPicture() {
    return Picture.builder().pictureInfo(getPictureInfo()).build();
  }

  public static Accommodation getAccommodation(LocalDate from, int duration, int roomNumber) {
    Picture picture = getPicture();
    Accommodation accommodation =
        Accommodation.builder()
            .name(faker.commerce().productName())
            .type(faker.options().option(AccommodationType.class))
            .rating(faker.number().numberBetween(1, 5))
            .phoneNumber(faker.phoneNumber().phoneNumber())
            .description(faker.shakespeare().hamletQuote())
            .address(getAddress())
            .build();

    // mapping rooms & picture
    List<Room> rooms = new LinkedList<>();
    for (int i = 0; i < roomNumber; i++) {
      int maxOccupancy = faker.number().numberBetween(1, 5);
      int quantity = faker.number().numberBetween(1, 5);
      rooms.add(getRoom(maxOccupancy, quantity, from, duration));
    }
    accommodation.createAccommodation(rooms, picture);

    return accommodation;
  }

  /**
   * @param maxOccupancy 객실 최대 인원
   * @param quantity 객실 재고량
   * @param from 객실 재고를 언제부터 관리할 것인지
   * @param duration 객실 재고 보관 기간
   * @return 객실 및 객실의 재고 정보
   */
  public static Room getRoom(int maxOccupancy, int quantity, LocalDate from, int duration) {
    Picture picture = getPicture();
    Room room =
        Room.builder()
            .name(faker.name().name())
            .price(faker.number().numberBetween(50000, 100000))
            .maxOccupancy(maxOccupancy)
            .build();

    room.addPicture(picture);
    from.datesUntil(from.plusDays(duration)).forEach(d -> room.addStock(getStock(d, quantity)));

    return room;
  }

  private static Stock getStock(LocalDate date, int quantity) {
    return new Stock(date, quantity);
  }

  public static Reservation getReservation(CheckDate checkDate) {
    Reservation reservation =
        Reservation.builder()
            .reservationNo(faker.random().toString())
            .status(faker.options().option(ReservationStatus.PAYMENT_PENDING))
            .checkDate(checkDate)
            .numberOfGuests(faker.number().numberBetween(1, 30))
            .guestName(faker.name().name())
            .guestPhone(faker.phoneNumber().phoneNumber())
            .deleted(false)
            .payment(null)
            .build();
    Room room = getRoom(10, 10, LocalDate.now(), 10);
    room.addReservation(reservation);

    Member member = getMember();
    reservation.setMember(member);

    return reservation;
  }
}
