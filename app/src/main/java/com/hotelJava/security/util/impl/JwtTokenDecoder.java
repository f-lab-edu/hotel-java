package com.hotelJava.security.util.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.hotelJava.common.error.ErrorCode;
import com.hotelJava.common.error.exception.BadRequestException;
import com.hotelJava.security.MemberDetails;
import com.hotelJava.security.config.JwtConfigurationProperties;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtTokenDecoder {

  private final JwtConfigurationProperties jwtConfig;

  public MemberDetails decode(String jwtToken) {
    DecodedJWT decodedJWT =
        verify(jwtToken).orElseThrow(() -> new BadRequestException(ErrorCode.AUTHENTICATION_FAIL));
    String email = decodedJWT.getSubject();
    List<? extends GrantedAuthority> authorities =
        decodedJWT.getClaim("roles").asList(SimpleGrantedAuthority.class);
    return new MemberDetails(email, "none", authorities);
  }

  private Optional<DecodedJWT> verify(String jwtToken) {
    DecodedJWT decodedJWT = null;
    try {
      JWTVerifier verifier = JWT.require(Algorithm.HMAC512(jwtConfig.secret())).build();
      decodedJWT = verifier.verify(jwtToken);
    } catch (JWTVerificationException e) {
      log.error("not verified JWT token");
    }
    return Optional.ofNullable(decodedJWT);
  }
}
