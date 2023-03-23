package com.hotelJava.member.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode
@Builder
@Entity
public class Member {

  @Id
  @GeneratedValue
  @Column(name = "ID")
  private Long id;

  @Column(name = "EMAIL")
  private String email;

  @Column(name = "NAME")
  private String name;

  @Column(name = "PASSWORD")
  private String password;

  @Column(name = "PHONE")
  private String phone;

  @Enumerated(value = EnumType.STRING)
  private Role role;

  @Enumerated(value = EnumType.STRING)
  private Grade grade;
}
