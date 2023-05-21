package com.hotelJava.member.adapter.out;

import com.hotelJava.member.application.port.out.MatchPasswordPort;
import com.hotelJava.member.application.port.out.EncryptPasswordPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PasswordAdapter implements EncryptPasswordPort, MatchPasswordPort {

  private final PasswordEncoder passwordEncoder;

  @Override
  public boolean matches(String rawPassword, String encryptedPassword) {
    return passwordEncoder.matches(rawPassword, encryptedPassword);
  }

  @Override
  public String encode(String rawPassword) {
    return passwordEncoder.encode(rawPassword);
  }
}
