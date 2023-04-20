package com.hotelJava.security.util.impl;

import java.util.List;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
@Builder
public class JwtPayload {
  private String subject;
  private List<String> roles;
}
