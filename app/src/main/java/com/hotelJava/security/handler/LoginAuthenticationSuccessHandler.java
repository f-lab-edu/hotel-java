package com.hotelJava.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotelJava.common.error.ErrorCode;
import com.hotelJava.common.error.exception.InternalServerException;
import com.hotelJava.security.dto.TokenDto;
import com.hotelJava.security.token.LoginPostAuthenticationToken;
import com.hotelJava.security.util.impl.JwtTokenFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoginAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

  private final JwtTokenFactory jwtTokenFactory;

  @Override
  public void onAuthenticationSuccess(
      HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
    LoginPostAuthenticationToken postAuthToken = (LoginPostAuthenticationToken) authentication;

    String token =
        jwtTokenFactory.generate(postAuthToken.getJwtPayload(), System.currentTimeMillis());

    writeResponse(response, token);
  }

  private void writeResponse(HttpServletResponse response, String token) {
    ObjectMapper objectMapper = new ObjectMapper();
    TokenDto dto = new TokenDto(token);
    try {
      response.getWriter().write(objectMapper.writeValueAsString(dto));
    } catch (IOException e) {
      log.error("problem with writing jwt token");
      throw new InternalServerException(ErrorCode.INTERNAL_SERVER_ERROR);
    }
  }
}
