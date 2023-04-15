package com.hotelJava.security.token;

import com.hotelJava.security.dto.LoginDto;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class LoginPreAuthenticationToken extends UsernamePasswordAuthenticationToken {
  public LoginPreAuthenticationToken(LoginDto dto) {
    super(dto.email(), dto.password());
  }

  public String getEmail() {
    return (String) super.getPrincipal();
  }

  public String getPassword() {
    return (String) super.getCredentials();
  }
}
