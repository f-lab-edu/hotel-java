package com.hotelJava.security.util.impl;

import com.hotelJava.member.dto.SignUpRequestDto;

public interface MemberPasswordEncoder {
  SignUpRequestDto encrypt(SignUpRequestDto signUpRequestDto);

  boolean matches(String requestPassword, String originPassword);
}
