package com.hotelJava.security.util.specification;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotelJava.common.error.ErrorCode;
import com.hotelJava.common.error.exception.InternalServerException;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtTokenFactory {

  private final ObjectMapper objectMapper;

  public String generate(JwtPayload payload, Algorithm algorithm) {
    JWTCreator.Builder builder = JWT.create();

    try {
      for (Map.Entry<String, Object> claim : payload.getClaims().entrySet()) {
        builder.withClaim(claim.getKey(), objectMapper.writeValueAsString(claim.getValue()));
      }
    } catch (JsonProcessingException e) {
      log.error("토큰 생성 과정에서 예외 발생");
      throw new InternalServerException(ErrorCode.BAD_CREDENTIAL);
    }

    return builder
        .withIssuer(payload.getIssuer())
        .withExpiresAt(payload.getExpired())
        .withSubject(payload.getSubject())
        .sign(algorithm);
  }
}
