package com.hotelJava.security.filter;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class FilterSkipMatcher implements RequestMatcher {

  private final AntPathRequestMatcher defaultMatcher;
  private final OrRequestMatcher skipMatcher;

  public FilterSkipMatcher(String defaultPath, RequestMatcher... skipPath) {
    this.defaultMatcher = new AntPathRequestMatcher(defaultPath);
    this.skipMatcher = new OrRequestMatcher(skipPath);
  }

  @Override
  public boolean matches(HttpServletRequest request) {
    return !skipMatcher.matches(request) && defaultMatcher.matches(request);
  }
}
