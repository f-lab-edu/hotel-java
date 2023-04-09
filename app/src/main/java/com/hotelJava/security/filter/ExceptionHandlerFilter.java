package com.hotelJava.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotelJava.common.error.exception.CommonException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExceptionHandlerFilter extends OncePerRequestFilter {

  private final ObjectMapper objectMapper;

  @Override
  protected void doFilterInternal(
      @NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      FilterChain filterChain)
      throws ServletException, IOException {
    try {
      filterChain.doFilter(request, response);
    } catch (CommonException exception) {
      writeResponseMessage(response, exception);
    }
  }

  private void writeResponseMessage(HttpServletResponse response, CommonException exception)
      throws IOException {
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setStatus(exception.getStatusCode());
    response.getWriter().write(objectMapper.writeValueAsString(exception.errorResult()));
  }
}
