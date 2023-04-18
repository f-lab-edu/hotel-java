package com.hotelJava.security.filter;

import com.hotelJava.common.error.exception.BadRequestException;
import com.hotelJava.member.MemberTestFixture;
import com.hotelJava.member.domain.Member;
import com.hotelJava.security.token.JwtPostAuthenticationToken;
import com.hotelJava.security.util.impl.JwtPayload;
import com.hotelJava.security.util.impl.JwtTokenFactory;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.Authentication;

@SpringBootTest
class JwtAuthenticationFilterTest {

  @Autowired private JwtAuthenticationFilter jwtFilter;
  @Autowired private JwtTokenFactory jwtTokenFactory;

  @Test
  @DisplayName("인증에 성공한다면 JwtPostAuthenticationToken 객체가 반환된다")
  void attemptAuthentication_Authentication_success() {
    // given
    MockHttpServletRequest request = new MockHttpServletRequest();
    MockHttpServletResponse response = new MockHttpServletResponse();
    String token = getJwtToken(MemberTestFixture.getMember(), System.currentTimeMillis());
    request.addHeader("Authorization", token);

    // when
    Authentication authentication = jwtFilter.attemptAuthentication(request, response);

    // then
    Assertions.assertThat(authentication).isInstanceOf(JwtPostAuthenticationToken.class);
  }

  @Test
  @DisplayName("인증에 실패한다면 BadRequestException 예외가 발생한다")
  void attemptAuthentication_Exception_failed() {
    // given
    MockHttpServletRequest request = new MockHttpServletRequest();
    MockHttpServletResponse response = new MockHttpServletResponse();

    // when
    Assertions.assertThatThrownBy(() -> jwtFilter.attemptAuthentication(request, response))
        .isInstanceOf(BadRequestException.class);
  }

  private String getJwtToken(Member member, long currentTime) {
    JwtPayload payload =
        JwtPayload.builder()
            .subject(member.getEmail())
            .roles(List.of(member.getRole().name()))
            .build();
    return jwtTokenFactory.generate(payload, currentTime);
  }
}
