package com.hotelJava.security.token;

import com.hotelJava.member.domain.Password;
import com.hotelJava.security.dto.LoginDto;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class LoginPreAuthenticationToken extends UsernamePasswordAuthenticationToken {
  public LoginPreAuthenticationToken(LoginDto dto) {
    super(dto.email(), Password.of(dto.password()));
  }

  public String getEmail() {
    return (String) super.getPrincipal();
  }

  public Password getPassword() {
    return (Password) super.getCredentials();
  }
}
