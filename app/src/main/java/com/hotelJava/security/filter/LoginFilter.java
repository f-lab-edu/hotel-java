package com.hotelJava.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotelJava.security.dto.LoginDto;
import com.hotelJava.security.token.EmailPasswordAuthenticationToken;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

@Slf4j
public class LoginFilter extends AbstractAuthenticationProcessingFilter {
  private final ObjectMapper objectMapper;

  public LoginFilter(String defaultUrl, ObjectMapper objectMapper) {
    super(defaultUrl);
    this.objectMapper = objectMapper;
  }

  @Override
  public Authentication attemptAuthentication(
      HttpServletRequest request, HttpServletResponse response)
      throws AuthenticationException, IOException {

    LoginDto loginDto = getLoginDto(request);

    EmailPasswordAuthenticationToken preAuthToken =
        EmailPasswordAuthenticationToken.getPreAuthenticationToken(loginDto);

    return getAuthenticationManager().authenticate(preAuthToken);
  }

  private LoginDto getLoginDto(HttpServletRequest request) throws IOException {
    return objectMapper.readValue(request.getInputStream(), LoginDto.class);
  }
}
