package com.hotelJava.member;

import com.github.javafaker.Faker;
import com.hotelJava.member.domain.Grade;
import com.hotelJava.member.domain.Member;
import com.hotelJava.member.domain.Role;
import com.hotelJava.member.dto.ChangeProfileRequestDto;
import com.hotelJava.member.dto.SignUpRequestDto;

public class MemberTestFixture {

  private static final Faker faker = Faker.instance();

  /** test fixture */
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
}
