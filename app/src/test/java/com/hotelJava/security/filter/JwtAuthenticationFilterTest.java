package com.hotelJava.security.filter;

import static org.mockito.Mockito.*;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.*;

import com.hotelJava.common.error.exception.BadRequestException;
import com.hotelJava.security.token.JwtPreAuthenticationToken;
import com.hotelJava.security.util.impl.HeaderTokenExtractor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class JwtAuthenticationFilterTest {

  private JwtAuthenticationFilter jwtFilter;
  @Spy private AuthenticationManager authenticationManager;
  @Spy private HeaderTokenExtractor extractor;

  @BeforeEach
  void setUp() {
    FilterSkipMatcher matcher =
        new FilterSkipMatcher(
            "/api/**", antMatcher(HttpMethod.POST, "/api/members"), antMatcher("/login"));
    jwtFilter = new JwtAuthenticationFilter(matcher, extractor);
    jwtFilter.setAuthenticationManager(authenticationManager);
  }

  @Test
  @DisplayName("HttpRequestHeader 안에 token 데이터가 있다면, AuthenticationManager.authenticate 메서드가 호출된다")
  void attemptAuthentication_Authentication_success() {
    // given
    MockHttpServletRequest request = new MockHttpServletRequest();
    MockHttpServletResponse response = new MockHttpServletResponse();
    doReturn("token").when(extractor).extract(nullable(String.class));

    // when
    jwtFilter.attemptAuthentication(request, response);

    // then
    Mockito.verify(authenticationManager).authenticate(any(JwtPreAuthenticationToken.class));
  }

  @Test
  @DisplayName("HttpRequestHeader 안에 token 데이터가 없다면, BadRequestException 예외가 발생한다")
  void attemptAuthentication_Exception_failed() {
    // given
    MockHttpServletRequest request = new MockHttpServletRequest();
    MockHttpServletResponse response = new MockHttpServletResponse();

    // when
    Assertions.assertThatThrownBy(() -> jwtFilter.attemptAuthentication(request, response))
        .isInstanceOf(BadRequestException.class);
  }
}
