package com.hotelJava.member.domain;

import static com.hotelJava.member.domain.Grade.NORMAL;
import static com.hotelJava.member.domain.Role.USER;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@Getter
@Builder
@Entity
public class Member implements ProfileInfo {

  @Id @GeneratedValue private Long id;

  private String email;

  private String name;

  private String password;

  private String phone;

  @Default
  @Enumerated(value = EnumType.STRING)
  private Role role = USER;

  @Default
  @Enumerated(value = EnumType.STRING)
  private Grade grade = NORMAL;
}
