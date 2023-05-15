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
  private String encryption;

  private Password(String encryption) {
    this.encryption = encryption;
  }

  public boolean matches(String trial) {
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    return passwordEncoder.matches(trial, encryption);
  }

  public static Password of(String plain) {
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    return new Password(passwordEncoder.encode(plain));
  }
}
