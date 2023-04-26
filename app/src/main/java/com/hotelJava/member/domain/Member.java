package com.hotelJava.member.domain;

import static com.hotelJava.member.domain.Grade.NORMAL;
import static com.hotelJava.member.domain.Role.USER;

import com.hotelJava.reservation.domain.Reservation;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
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
public class Member implements Profile {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String email;

  private String name;

  private String password;

  private String phone;

  @OneToMany(mappedBy = "member")
  @Builder.Default
  private List<Reservation> reservations = new ArrayList<>();

  @Default
  @Enumerated(value = EnumType.STRING)
  private Role role = USER;

  @Default
  @Enumerated(value = EnumType.STRING)
  private Grade grade = NORMAL;

  public void changePassword(String password) {
    this.password = password;
  }

  public void changeProfile(Profile profileInfo) {
    this.name = profileInfo.getName();
    this.phone = profileInfo.getPhone();
  }
}
