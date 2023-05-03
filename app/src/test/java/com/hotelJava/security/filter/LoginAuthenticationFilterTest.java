package com.hotelJava.security.filter;

import static org.mockito.Mockito.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import com.hotelJava.common.error.exception.BadRequestException;
import com.hotelJava.security.dto.LoginDto;
import com.hotelJava.security.token.LoginPreAuthenticationToken;
import java.io.IOException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;

@SpringBootTest
class LoginAuthenticationFilterTest {

  private final Faker faker = Faker.instance();
  private final ObjectMapper objectMapper = new ObjectMapper();
  private final LoginAuthenticationFilter loginFilter = new LoginAuthenticationFilter("/login");
  @Spy private AuthenticationManager authenticationManager;

  @BeforeEach
  void setUp() {
    loginFilter.setAuthenticationManager(authenticationManager);
  }

  @Test
  @DisplayName("HttpRequestBody 안에 LoginDto 데이터가 있다면, AuthenticationManager.authenticate 메서드가 호출된다")
  void attemptAuthentication_Authentication_success() throws IOException {
    // given
    MockHttpServletRequest request = new MockHttpServletRequest();
    MockHttpServletResponse response = new MockHttpServletResponse();
    String email = faker.internet().emailAddress();
    String password = faker.internet().password();
    request.setContent(objectMapper.writeValueAsBytes(new LoginDto(email, password)));

    // when
    loginFilter.attemptAuthentication(request, response);

    // then
    verify(authenticationManager).authenticate(any(LoginPreAuthenticationToken.class));
  }

  @Test
  @DisplayName("Http 요청의 바디에 LoginDto 데이터가 없다면, BadRequestException 예외가 발생한다")
  void attemptAuthentication_Exception_failed() {
    // given
    MockHttpServletRequest request = new MockHttpServletRequest();
    MockHttpServletResponse response = new MockHttpServletResponse();

    // when, then
    Assertions.assertThatThrownBy(() -> loginFilter.attemptAuthentication(request, response))
        .isInstanceOf(BadRequestException.class);
  }
}
