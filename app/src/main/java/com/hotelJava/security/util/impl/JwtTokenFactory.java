package com.hotelJava.security.util.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.hotelJava.security.JwtConfigurationProperties;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtTokenFactory {

  private final JwtConfigurationProperties jwtConfig;

  public String generate(JwtPayload payload, long currentTime) {
    return JWT.create()
        .withSubject(payload.getSubject())
        .withClaim("roles", payload.getRoles())
        .withExpiresAt(new Date(currentTime + jwtConfig.validityTime().toMillis()))
        .withIssuer(jwtConfig.issuer())
        .sign(Algorithm.HMAC512(jwtConfig.secret()));
  }
}
