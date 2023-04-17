package com.hotelJava.security.util.impl;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class JwtPayload {
  private String issuer;
  private String subject;
  private List<String> roles;
}
