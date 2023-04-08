package com.hotelJava.security.token;

import com.hotelJava.security.dto.LoginDto;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class PreAuthenticationToken extends UsernamePasswordAuthenticationToken {

  public PreAuthenticationToken(Object principal, Object credentials) {
    super(principal, credentials);
  }

  public static PreAuthenticationToken generate(LoginDto dto) {
    return new PreAuthenticationToken(dto.email(), dto.password());
  }

  public String getEmail() {
    return (String) super.getPrincipal();
  }

  public String getPassword() {
    return (String) super.getCredentials();
  }
}
