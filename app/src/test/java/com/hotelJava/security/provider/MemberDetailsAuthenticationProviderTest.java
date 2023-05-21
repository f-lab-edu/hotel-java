package com.hotelJava.security.provider;

import com.hotelJava.TestFixture;
import com.hotelJava.common.error.exception.BadRequestException;
import com.hotelJava.common.error.exception.InternalServerException;
import com.hotelJava.member.application.port.out.MatchPasswordPort;
import com.hotelJava.member.domain.Member;
import com.hotelJava.security.MemberDetails;
import com.hotelJava.security.MemberDetailsService;
import com.hotelJava.security.dto.LoginDto;
import com.hotelJava.security.token.LoginPostAuthenticationToken;
import com.hotelJava.security.token.LoginPreAuthenticationToken;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.security.core.Authentication;

import static org.mockito.ArgumentMatchers.anyString;

@SpringBootTest
class MemberDetailsAuthenticationProviderTest {

  @Autowired private MemberDetailsAuthenticationProvider provider;
  @SpyBean private MemberDetailsService memberDetailsService;
  @SpyBean private MatchPasswordPort matchPasswordPort;

  @Test
  @DisplayName("올바른 로그인요청 LoginDto 가 주어졌을 때, 인증작업은 LoginPostAuthenticationToken 을 발행한다")
  void 인증성공() {
    // given
    Member member = TestFixture.getMember();

    MemberDetails loginMemberDetails = new MemberDetails(member);
    LoginPreAuthenticationToken preAuthentication =
        new LoginPreAuthenticationToken(new LoginDto(member.getEmail(), "1234"));

    Mockito.doReturn(loginMemberDetails).when(memberDetailsService).loadUserByUsername(anyString());
    Mockito.doReturn(true).when(matchPasswordPort).matches(anyString(), anyString());

    // when
    Authentication postAuthentication = provider.authenticate(preAuthentication);

    // then
    Assertions.assertThat(postAuthentication)
        .isEqualTo(new LoginPostAuthenticationToken(loginMemberDetails));
  }

  @Test
  @DisplayName("탈퇴한 회원에 대한 로그인 요청 LoginDto 가 주어졌을 때, 인증작업은 BadRequestException 을 발생시킨다")
  void 인증실패_탈퇴한회원() {
    // given
    Member member = TestFixture.getMember();
    member.deleteAccount();
    MemberDetails loginMemberDetails = new MemberDetails(member);
    LoginPreAuthenticationToken preAuthentication =
        new LoginPreAuthenticationToken(new LoginDto(member.getEmail(), "1234"));

    Mockito.doReturn(loginMemberDetails).when(memberDetailsService).loadUserByUsername(anyString());

    // when, then
    Assertions.assertThatThrownBy(() -> provider.authenticate(preAuthentication))
        .isInstanceOf(BadRequestException.class);
  }

  @Test
  @DisplayName("비밀번호가 틀린 로그인 요청 LoginDto 가 주어졌을 때, 인증작업은 BadRequestException 을 발생시킨다")
  void 인증실패_틀린비밀번호() {
    // given
    Member member = TestFixture.getMember();
    MemberDetails loginMemberDetails = new MemberDetails(member);
    LoginPreAuthenticationToken preAuthentication =
        new LoginPreAuthenticationToken(new LoginDto(member.getEmail(), member.getPassword()));

    Mockito.doReturn(loginMemberDetails).when(memberDetailsService).loadUserByUsername(anyString());
    Mockito.doReturn(false).when(matchPasswordPort).matches(anyString(), anyString());

    // when, then
    Assertions.assertThatThrownBy(() -> provider.authenticate(preAuthentication))
        .isInstanceOf(BadRequestException.class);
  }

  @Test
  @DisplayName("PreAuthentication 이 null 일 때, 인증작업은 InternalRequestException 을 발생시킨다")
  void 인증실패_토큰_null() {
    Assertions.assertThatThrownBy(() -> provider.authenticate(null))
        .isInstanceOf(InternalServerException.class);
  }
}
