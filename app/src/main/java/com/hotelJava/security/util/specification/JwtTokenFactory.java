package com.hotelJava.security.util.specification;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.hotelJava.common.error.ErrorCode;
import com.hotelJava.common.error.exception.InternalServerException;
import com.hotelJava.security.util.impl.WebTokenFactory;
import java.util.Date;
import java.util.List;
import org.springframework.security.core.userdetails.UserDetails;

public class JwtTokenFactory implements WebTokenFactory {

  private static final String AUTHORITY_KEY = "ROLE";
  private static final String PRINCIPAL_KEY = "EMAIL";
  private static final String TOKEN_ISSUER = "hotelJava";
  private static final String SECRET_KEY = "hotelJava-jwt-secret";
  private static final long TOKEN_VALID_DURATION = 1000 * 60 * 10; // 10분

  @Override
  public String generate(UserDetails userDetails) {
    try {
      return JWT.create()
          .withIssuer(TOKEN_ISSUER)
          .withExpiresAt(new Date(System.currentTimeMillis() + TOKEN_VALID_DURATION))
          .withClaim(PRINCIPAL_KEY, userDetails.getUsername())
          .withClaim(AUTHORITY_KEY, List.of(userDetails.getAuthorities()))
          .sign(Algorithm.HMAC512(SECRET_KEY));
    } catch (Exception e) {
      throw new InternalServerException(ErrorCode.LOGIN_TOKEN_ERROR);
    }
  }
}
