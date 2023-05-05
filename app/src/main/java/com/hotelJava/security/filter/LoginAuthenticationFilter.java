package com.hotelJava.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotelJava.common.error.ErrorCode;
import com.hotelJava.common.error.exception.BadRequestException;
import com.hotelJava.security.dto.LoginDto;
import com.hotelJava.security.token.LoginPreAuthenticationToken;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

@Slf4j
public class LoginAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

  public LoginAuthenticationFilter(String defaultUrl) {
    super(defaultUrl);
  }

  @Override
  public Authentication attemptAuthentication(
      HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
    log.trace("login request received");
    LoginDto loginDto = getLoginDto(request);
    LoginPreAuthenticationToken preAuthToken = new LoginPreAuthenticationToken(loginDto);

    return getAuthenticationManager().authenticate(preAuthToken);
  }

  private LoginDto getLoginDto(HttpServletRequest request) {
    try {
      return new ObjectMapper().readValue(request.getInputStream(), LoginDto.class);
    } catch (IOException e) {
      log.error("login dto read failed", e);
      throw new BadRequestException(ErrorCode.BAD_REQUEST_ERROR);
    }
  }
}
