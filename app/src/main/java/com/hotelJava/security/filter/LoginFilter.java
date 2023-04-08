package com.hotelJava.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotelJava.security.dto.LoginDto;
import com.hotelJava.security.token.PreAuthenticationToken;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

@Slf4j
public class LoginFilter extends AbstractAuthenticationProcessingFilter {
  @Autowired private ObjectMapper objectMapper;

  public LoginFilter(String defaultUrl) {
    super(defaultUrl);
  }

  @Override
  public Authentication attemptAuthentication(
      HttpServletRequest request, HttpServletResponse response)
      throws AuthenticationException, IOException {

    LoginDto loginDto = getLoginDto(request);
    PreAuthenticationToken preAuthToken = PreAuthenticationToken.generate(loginDto);

    return getAuthenticationManager().authenticate(preAuthToken);
  }

  private LoginDto getLoginDto(HttpServletRequest request) throws IOException {
    return objectMapper.readValue(request.getInputStream(), LoginDto.class);
  }
}
