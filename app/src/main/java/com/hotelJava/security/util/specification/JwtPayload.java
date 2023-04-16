package com.hotelJava.security.util.specification;

import lombok.Builder;
import lombok.Getter;
import java.util.Date;
import java.util.Map;

@Getter
@Builder
public class JwtPayload {

  private String issuer;
  private String subject;
  private Date expired;
  private Map<String, Object> claims;
}
