package com.hotelJava;

import com.github.javafaker.Faker;
import com.hotelJava.accommodation.domain.Accommodation;
import com.hotelJava.accommodation.domain.AccommodationType;
import com.hotelJava.common.embeddable.Address;
import com.hotelJava.inventory.Inventory;
import com.hotelJava.member.domain.Grade;
import com.hotelJava.member.domain.Member;
import com.hotelJava.member.domain.Role;
import com.hotelJava.member.dto.ChangeProfileRequestDto;
import com.hotelJava.member.dto.SignUpRequestDto;
import com.hotelJava.picture.domain.Picture;
import com.hotelJava.picture.domain.PictureInfo;
import com.hotelJava.room.domain.Room;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class TestFixture {

  private static final Faker faker = Faker.instance();

  /** dto test fixture * */
  public static SignUpRequestDto getSignUpDto() {
    return SignUpRequestDto.builder()
        .email(faker.internet().emailAddress())
        .password(faker.internet().password())
        .name(faker.name().name())
        .phone(faker.phoneNumber().phoneNumber())
        .build();
  }

  public static ChangeProfileRequestDto getChangeProfileDto() {
    return ChangeProfileRequestDto.builder()
        .name(faker.name().name())
        .phone(faker.phoneNumber().phoneNumber())
        .build();
  }

  /** domain test fixture * */
  public static Member getMember() {
    return Member.builder()
        .email(faker.internet().emailAddress())
        .password(faker.internet().password())
        .name(faker.name().name())
        .phone(faker.phoneNumber().phoneNumber())
        .role(faker.options().option(Role.values()))
        .grade(faker.options().option(Grade.values()))
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

  public static Accommodation getAccommodation(LocalDate start, int duration, int roomNumber) {
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
      rooms.add(getRoom(faker.number().numberBetween(1, 5)));
    }
    accommodation.createAccommodation(rooms, picture);

    // mapping inventory

    rooms.forEach(
        room ->
            start
                .datesUntil(start.plusDays(duration))
                .forEach(date -> getInventory(room, date, faker.number().numberBetween(1, 5))));

    return accommodation;
  }

  public static Room getRoom(int maxOccupancy) {
    Picture picture = getPicture();
    Room room =
        Room.builder()
            .name(faker.name().name())
            .price(faker.number().numberBetween(50000, 100000))
            .maxOccupancy(maxOccupancy)
            .build();
    room.addPicture(picture);
    return room;
  }

  public static Inventory getInventory(Room room, LocalDate date, int quantity) {
    Inventory inventory = new Inventory(date, quantity);
    room.addInventory(inventory);
    return inventory;
  }
}
