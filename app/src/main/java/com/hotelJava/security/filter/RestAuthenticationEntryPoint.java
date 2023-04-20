package com.hotelJava.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotelJava.common.error.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
  private final ObjectMapper objectMapper;

  @Override
  public void commence(
      HttpServletRequest request, HttpServletResponse response, AuthenticationException ex)
      throws IOException {
    log.error("Unauthorized user request called");
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setHeader(
        "WWW-Authenticate", "try login at path \"/login\" with json value{email,password}");
    objectMapper.writeValue(response.getWriter(), ErrorCode.AUTHENTICATION_FAIL);
  }
}
