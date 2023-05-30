package com.hotelJava;

import com.github.javafaker.Faker;
import com.hotelJava.accommodation.domain.Accommodation;
import com.hotelJava.accommodation.domain.Room;
import com.hotelJava.accommodation.domain.Stock;
import com.hotelJava.common.embeddable.CheckDate;
import com.hotelJava.common.embeddable.Money;
import com.hotelJava.member.domain.Member;
import com.hotelJava.reservation.domain.Reservation;
import com.hotelJava.reservation.domain.ReservationStatus;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DomainTestFixture {

  private static final Faker faker = Faker.instance();

  /** domain test fixture * */
  public static Member member() {
    return Member.builder()
        .email(FakerData.email())
        .name(FakerData.personName())
        .password(FakerData.password())
        .phone(FakerData.phone())
        .build();
  }

  public static Accommodation accommodation() {
    return Accommodation.builder()
        .name(FakerData.accommodationName())
        .type(FakerData.accommodationType())
        .address(FakerData.address())
        .phoneNumber(FakerData.phone())
        .description("example description")
        .build();
  }

  public static Accommodation accommodationWithRooms(int... maxOccupancy) {
    Accommodation accommodation = accommodation();
    for (int m : maxOccupancy) {
      accommodation.addRoom(room(m));
    }
    return accommodation;
  }

  public static Accommodation accommodationWithRooms(Money... price) {
    Accommodation accommodation = accommodation();
    for (Money money : price) {
      accommodation.addRoom(room(money));
    }
    return accommodation;
  }

  public static Accommodation accommodationWithRooms(
      LocalDate from, int duration, int size, long minMoney, long maxMoney) {
    Accommodation accommodation = accommodation();
    for (int i = 0; i < size; i++) {
      accommodation.addRoom(room(from, duration, FakerData.money(minMoney, maxMoney)));
    }
    return accommodation;
  }

  public static Accommodation accommodationWithRooms(LocalDate from, int duration, int quantity) {
    Accommodation accommodation = accommodation();
    accommodation.addRoom(room(from, duration, quantity));
    return accommodation;
  }

  public static List<Accommodation> accommodationsWithRooms() {
    List<Accommodation> accommodations = new ArrayList<>();

    for (int i = 0; i < 100; i++) {
      accommodations.add(accommodation());
    }

    accommodations.forEach(
        accommodation -> {
          for (int i = 0; i < faker.number().numberBetween(1, 5); i++) {
            accommodation.addRoom(room());
          }
        });
    return accommodations;
  }

  public static Room room(Money price) {
    Room room = Room.builder().price(price).build();
    addStock(room, LocalDate.now(), 1, 1);
    return room;
  }

  public static Room room(LocalDate from, int duration, int quantity, Money price) {
    Room room = Room.builder().price(price).build();
    addStock(room, from, duration, quantity);
    return room;
  }

  public static Room room(LocalDate from, int duration, Money price) {
    Room room = Room.builder().price(price).build();
    addStock(room, from, duration, 1);
    return room;
  }

  public static Room room(LocalDate from, int duration, int quantity) {
    Room room = Room.builder().build();
    addStock(room, from, duration, quantity);
    return room;
  }

  public static Room room(int maxOccupancy) {
    Room room = Room.builder().maxOccupancy(maxOccupancy).build();
    addStock(room, LocalDate.now(), 1, 1);
    return room;
  }

  public static Room room() {
    Room room = Room.builder().build();
    addStock(room, LocalDate.now(), 1, 1);
    return room;
  }

  private static void addStock(Room room, LocalDate from, int duration, int quantity) {
    from.datesUntil(from.plusDays(duration)).forEach(d -> room.addStock(new Stock(d, quantity)));
  }

  public static Reservation reservation(CheckDate checkDate) {
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
    Room room = room(LocalDate.now(), 10, 1);
    room.addReservation(reservation);

    Member member = member();
    reservation.setMember(member);

    return reservation;
  }
}
