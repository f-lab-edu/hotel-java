package com.hotelJava.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import com.hotelJava.common.error.exception.BadRequestException;
import com.hotelJava.member.MemberTestFixture;
import com.hotelJava.member.dto.SignUpRequestDto;
import com.hotelJava.member.service.MemberService;
import com.hotelJava.security.dto.LoginDto;
import com.hotelJava.security.token.LoginPostAuthenticationToken;
import java.io.IOException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.Authentication;

@SpringBootTest
class LoginAuthenticationFilterTest {

  private final ObjectMapper objectMapper = new ObjectMapper();
  @Autowired private LoginAuthenticationFilter loginFilter;
  @Autowired private MemberService memberService;

  @Test
  @DisplayName("인증에 성공한다면 Authentication 객체가 리턴된다")
  void attemptAuthentication_Authentication_success() throws IOException {
    // given
    MockHttpServletRequest request = new MockHttpServletRequest();
    MockHttpServletResponse response = new MockHttpServletResponse();

    SignUpRequestDto signUpDto = MemberTestFixture.getSignUpDto();
    memberService.signUp(signUpDto);

    // when
    LoginDto dto = new LoginDto(signUpDto.getEmail(), signUpDto.getPassword());
    request.setContent(objectMapper.writeValueAsBytes(dto));
    Authentication authentication = loginFilter.attemptAuthentication(request, response);

    // then
    Assertions.assertThat(authentication).isInstanceOf(LoginPostAuthenticationToken.class);
  }

  @Test
  @DisplayName("인증에 실패한다면 BadRequestException 예외가 발생한다")
  void attemptAuthentication_Exception_failed() throws IOException {
    // given
    MockHttpServletRequest request = new MockHttpServletRequest();
    MockHttpServletResponse response = new MockHttpServletResponse();

    String email = Faker.instance().internet().emailAddress();
    String password = Faker.instance().internet().password();

    // when
    LoginDto dto = new LoginDto(email, password);
    request.setContent(objectMapper.writeValueAsBytes(dto));

    // then
    Assertions.assertThatThrownBy(() -> loginFilter.attemptAuthentication(request, response))
        .isInstanceOf(BadRequestException.class);
  }
}
