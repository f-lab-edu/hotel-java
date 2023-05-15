package com.hotelJava.member.domain;

import static com.hotelJava.member.domain.Grade.NORMAL;
import static com.hotelJava.member.domain.Role.USER;

import com.hotelJava.member.domain.specification.Authority;
import com.hotelJava.member.domain.specification.Credential;
import com.hotelJava.member.domain.specification.Identifier;
import com.hotelJava.member.domain.specification.Profile;
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
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Where(clause = "deleted = false")
@SQLDelete(sql = "UPDATE member SET deleted = true WHERE id = ?")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Builder
@Entity
public class Member implements Authority, Credential, Identifier, Profile {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String email;

  private String name;

  @Embedded
  @AttributeOverride(name = "value", column = @Column(name = "password"))
  private Password password;

  private String phone;

  private boolean deleted;

  @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
  @Builder.Default
  private List<Reservation> reservations = new ArrayList<>();

  @Default
  @Enumerated(value = EnumType.STRING)
  private Role role = USER;

  @Default
  @Enumerated(value = EnumType.STRING)
  private Grade grade = NORMAL;

  public Member(String email, String name, String plainPassword, String phone) {
    this.email = email;
    this.name = name;
    this.password = Password.of(plainPassword);
    this.phone = phone;
  }

  public void changePassword(String password) {
    this.password = Password.of(password);
  }

  public void changeProfile(Profile profileInfo) {
    this.name = profileInfo.getName();
    this.phone = profileInfo.getPhone();
  }

  public void deleteAccount() {
    this.deleted = true;
  }
}
