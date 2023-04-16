package com.hotelJava.security.handler;

import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotelJava.security.config.JwtConfig;
import com.hotelJava.security.token.PostAuthenticationToken;
import com.hotelJava.security.util.specification.JwtPayload;
import com.hotelJava.security.util.specification.JwtTokenFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoginAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

  private final JwtTokenFactory jwtTokenFactory;
  private final ObjectMapper objectMapper;

  @Override
  public void onAuthenticationSuccess(
      HttpServletRequest request, HttpServletResponse response, Authentication authentication)
      throws IOException {

    PostAuthenticationToken postAuthToken = (PostAuthenticationToken) authentication;

    Date expireTime = new Date(System.currentTimeMillis() + JwtConfig.EXPIRE_TIME);
    JwtPayload payload = postAuthToken.getJwtPayload(expireTime);
    Algorithm algorithm = Algorithm.HMAC512(JwtConfig.SECRET);

    String token = jwtTokenFactory.generate(payload, algorithm);

    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setHeader("Authorization", objectMapper.writeValueAsString(token));
  }
}
