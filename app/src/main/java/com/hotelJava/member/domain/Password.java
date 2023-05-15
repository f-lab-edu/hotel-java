package com.hotelJava.member.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EqualsAndHashCode
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Password {
  private String encrypted;

  private Password(String encrypted) {
    this.encrypted = encrypted;
  }

  public boolean matches(String rawPassword) {
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    return passwordEncoder.matches(rawPassword, encrypted);
  }

  public static Password of(String rawPassword) {
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    return new Password(passwordEncoder.encode(rawPassword));
  }
}
