package com.hotelJava.member.domain;

import com.hotelJava.reservation.domain.Reservation;
import jakarta.persistence.*;
import lombok.*;
import lombok.Builder.Default;

import java.util.ArrayList;
import java.util.List;

import static com.hotelJava.member.domain.Grade.NORMAL;
import static com.hotelJava.member.domain.Role.USER;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@Getter
@Builder
@Entity
public class Member implements ProfileInfo {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String email;

  private String name;

  private String password;

  private String phone;

  @OneToMany(mappedBy = "member")
  private List<Reservation> reservations = new ArrayList<>();

  @Default
  @Enumerated(value = EnumType.STRING)
  private Role role = USER;

  @Default
  @Enumerated(value = EnumType.STRING)
  private Grade grade = NORMAL;
}
