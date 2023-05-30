package com.hotelJava;

import com.hotelJava.accommodation.application.port.in.command.AddRoomCommand;
import com.hotelJava.accommodation.application.port.in.command.RegisterAccommodationCommand;
import com.hotelJava.accommodation.application.port.in.command.UpdateAccommodationCommand;
import com.hotelJava.common.embeddable.CheckDate;
import com.hotelJava.common.embeddable.Money;
import com.hotelJava.member.application.port.in.command.ChangeProfileCommand;
import com.hotelJava.member.application.port.in.command.MemberSignUpCommand;
import com.hotelJava.reservation.application.port.in.command.ReserveCommand;
import com.hotelJava.reservation.domain.ReserveType;
import java.util.List;

public class CommandTestFixture {

  public static MemberSignUpCommand memberSignUpCommand() {
    return MemberSignUpCommand.builder()
        .email(FakerData.email())
        .rawPassword(FakerData.password())
        .name(FakerData.personName())
        .phone(FakerData.phone())
        .build();
  }

  public static ChangeProfileCommand changeProfileCommand() {
    return ChangeProfileCommand.builder()
        .name(FakerData.personName())
        .phone(FakerData.phone())
        .build();
  }

  public static RegisterAccommodationCommand registerAccommodationCommand() {
    return RegisterAccommodationCommand.builder()
        .name(FakerData.accommodationName())
        .phoneNumber(FakerData.phone())
        .type(FakerData.accommodationType())
        .address(FakerData.address())
        .picture(FakerData.picture())
        .description("example description")
        .build();
  }

  public static UpdateAccommodationCommand updateAccommodationCommand() {
    return UpdateAccommodationCommand.builder()
        .name(FakerData.accommodationName())
        .phoneNumber(FakerData.phone())
        .type(FakerData.accommodationType())
        .picture(FakerData.picture())
        .address(FakerData.address())
        .description("example description")
        .build();
  }

  public static AddRoomCommand addRoomCommand() {
    return AddRoomCommand.builder()
        .pictures(List.of(FakerData.picture()))
        .name(FakerData.roomName())
        .price(Money.of(FakerData.price()))
        .maxOccupancy(FakerData.maxOccupancy())
        .build();
  }

  public static ReserveCommand reserveCommand(
      ReserveType reserveType, CheckDate checkDate, int numberOfGuests) {
    return ReserveCommand.builder()
        .reserveType(reserveType)
        .guestName(FakerData.personName())
        .guestPhone(FakerData.phone())
        .numberOfGuests(numberOfGuests)
        .checkDate(checkDate)
        .build();
  }
}
