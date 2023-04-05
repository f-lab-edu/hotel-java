package com.hotelJava.member.util;

import com.hotelJava.member.dto.SignUpRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BCryptPasswordEncoder implements MemberPasswordEncoder {
  private final PasswordEncoder passwordEncoder;

  public SignUpRequestDto encrypt(SignUpRequestDto dto) {
    String encryptedPassword = passwordEncoder.encode(dto.getPassword());
    return SignUpRequestDto.builder()
        .email(dto.getEmail())
        .name(dto.getName())
        .phone(dto.getPhone())
        .password(encryptedPassword)
        .build();
  }

  public boolean matches(String requestPassword, String originPassword) {
    return passwordEncoder.matches(requestPassword, originPassword);
  }
}
