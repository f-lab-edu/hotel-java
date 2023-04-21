package com.hotelJava.security.handler;

import com.hotelJava.member.MemberTestFixture;
import com.hotelJava.member.domain.Member;
import com.hotelJava.security.MemberDetails;
import com.hotelJava.security.token.LoginPostAuthenticationToken;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.Authentication;

@SpringBootTest
class LoginAuthenticationSuccessHandlerTest {

  @Autowired private LoginAuthenticationSuccessHandler successHandler;

  @Test
  @DisplayName("로그인에 성공하면 응답 메시지의 Authorization 헤더에 토큰값을 남긴다")
  void onAuthenticationSuccess() {
    // given
    Member member = MemberTestFixture.getMember();
    MemberDetails memberDetails = new MemberDetails(member);

    MockHttpServletRequest request = new MockHttpServletRequest();
    MockHttpServletResponse response = new MockHttpServletResponse();
    Authentication authentication = new LoginPostAuthenticationToken(memberDetails);

    // when
    successHandler.onAuthenticationSuccess(request, response, authentication);

    // then
    Assertions.assertThat(response.getHeader("Authorization")).isNotNull();
  }
}
