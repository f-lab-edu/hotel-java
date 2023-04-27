package com.hotelJava.security.filter;

import com.hotelJava.common.error.ErrorCode;
import com.hotelJava.common.error.exception.BadRequestException;
import com.hotelJava.security.token.JwtPreAuthenticationToken;
import com.hotelJava.security.util.impl.HeaderTokenExtractor;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Slf4j
public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

  private final HeaderTokenExtractor extractor;

  public JwtAuthenticationFilter(RequestMatcher requestMatcher, HeaderTokenExtractor extractor) {
    super(requestMatcher);
    this.extractor = extractor;
  }

  @Override
  public Authentication attemptAuthentication(
      HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
    log.trace("jwt login request received");
    String jwtToken = extractor.extract(request.getHeader("Authorization"));

    if (jwtToken == null) {
      log.error("authorization is null");
      throw new BadRequestException(ErrorCode.AUTHENTICATION_FAIL);
    }

    JwtPreAuthenticationToken preAuthToken = new JwtPreAuthenticationToken(jwtToken);

    return super.getAuthenticationManager().authenticate(preAuthToken);
  }

  @Override
  protected void successfulAuthentication(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain chain,
      Authentication authResult)
      throws IOException, ServletException {
    SecurityContext context = SecurityContextHolder.createEmptyContext();
    context.setAuthentication(authResult);
    SecurityContextHolder.setContext(context);
    chain.doFilter(request, response);
  }

  @Override
  protected void unsuccessfulAuthentication(
      HttpServletRequest request, HttpServletResponse response, AuthenticationException failed)
      throws ServletException, IOException {
    SecurityContextHolder.clearContext();
    super.getFailureHandler().onAuthenticationFailure(request, response, failed);
  }
}
