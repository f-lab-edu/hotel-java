package com.hotelJava.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import com.hotelJava.TestFixture;
import com.hotelJava.member.domain.Member;
import com.hotelJava.security.MemberDetails;
import com.hotelJava.security.dto.TokenDto;
import com.hotelJava.security.token.LoginPostAuthenticationToken;
import com.hotelJava.security.util.impl.JwtPayload;
import com.hotelJava.security.util.impl.JwtTokenFactory;
import java.io.IOException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.Authentication;

@SpringBootTest
class LoginAuthenticationSuccessHandlerTest {

  private final Faker faker = Faker.instance();
  private final ObjectMapper objectMapper = new ObjectMapper();
  @Autowired private LoginAuthenticationSuccessHandler successHandler;
  @SpyBean JwtTokenFactory jwtTokenFactory;

  @Test
  @DisplayName("로그인에 성공하면 응답 메시지에 토큰값을 남긴다")
  void onAuthenticationSuccess() throws IOException {
    // given
    Member member = TestFixture.getMember();
    MemberDetails memberDetails = new MemberDetails(member);

    MockHttpServletRequest request = new MockHttpServletRequest();
    MockHttpServletResponse response = new MockHttpServletResponse();
    Authentication authentication = new LoginPostAuthenticationToken(memberDetails);

    String token = faker.letterify("?????");

    Mockito.doReturn(token)
        .when(jwtTokenFactory)
        .generate(Mockito.any(JwtPayload.class), Mockito.anyLong());

    // when
    successHandler.onAuthenticationSuccess(request, response, authentication);

    // then
    Assertions.assertThat(objectMapper.readValue(response.getContentAsByteArray(), TokenDto.class))
        .isEqualTo(new TokenDto(token));
  }
}
