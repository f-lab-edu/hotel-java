package com.hotelJava.security.provider;

import com.hotelJava.common.error.exception.BadRequestException;
import com.hotelJava.common.error.exception.InternalServerException;
import com.hotelJava.TestFixture;
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
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class MemberDetailsAuthenticationProviderTest {

  @Autowired private MemberDetailsAuthenticationProvider provider;
  @SpyBean private MemberDetailsService memberDetailsService;
  @Autowired private PasswordEncoder passwordEncoder;

  @Test
  @DisplayName("올바른 로그인요청 LoginDto 가 주어졌을 때, 인증작업은 LoginPostAuthenticationToken 을 발행한다")
  void authenticate_success() {
    // given
    Member member = TestFixture.getMember();
    String originPassword = member.getPassword();
    String encodedPassword = passwordEncoder.encode(originPassword);
    member.changePassword(encodedPassword);

    MemberDetails loginMemberDetails = new MemberDetails(member);
    LoginPreAuthenticationToken preAuthentication =
        new LoginPreAuthenticationToken(new LoginDto(member.getEmail(), originPassword));

    Mockito.doReturn(loginMemberDetails)
        .when(memberDetailsService)
        .loadUserByUsername(Mockito.anyString());

    // when
    Authentication postAuthentication = provider.authenticate(preAuthentication);

    // then
    Assertions.assertThat(postAuthentication)
        .isEqualTo(new LoginPostAuthenticationToken(loginMemberDetails));
  }

  @Test
  @DisplayName("탈퇴한 회원에 대한 로그인 요청 LoginDto 가 주어졌을 때, 인증작업은 BadRequestException 을 발생시킨다")
  void authenticate_deletedAccount_BadRequestException() {
    // given
    Member member = TestFixture.getMember();
    member.deleteAccount();
    MemberDetails loginMemberDetails = new MemberDetails(member);
    LoginPreAuthenticationToken preAuthentication =
        new LoginPreAuthenticationToken(new LoginDto(member.getEmail(), member.getPassword()));

    Mockito.doReturn(loginMemberDetails)
        .when(memberDetailsService)
        .loadUserByUsername(Mockito.anyString());

    // when, then
    Assertions.assertThatThrownBy(() -> provider.authenticate(preAuthentication))
        .isInstanceOf(BadRequestException.class);
  }

  @Test
  @DisplayName("비밀번호가 틀린 로그인 요청 LoginDto 가 주어졌을 때, 인증작업은 BadRequestException 을 발생시킨다")
  void authenticate_wrongPassword_BadRequestException() {
    // given
    Member member = TestFixture.getMember();
    MemberDetails loginMemberDetails = new MemberDetails(member);
    LoginPreAuthenticationToken preAuthentication =
        new LoginPreAuthenticationToken(
            new LoginDto(member.getEmail(), member.getPassword() + "mistake"));

    Mockito.doReturn(loginMemberDetails)
        .when(memberDetailsService)
        .loadUserByUsername(Mockito.anyString());

    // when, then
    Assertions.assertThatThrownBy(() -> provider.authenticate(preAuthentication))
        .isInstanceOf(BadRequestException.class);
  }

  @Test
  @DisplayName("PreAuthentication 이 null 일 때, 인증작업은 InternalRequestException 을 발생시킨다")
  void authenticate_PreAuthentication_null_InternalException() {
    Assertions.assertThatThrownBy(() -> provider.authenticate(null))
        .isInstanceOf(InternalServerException.class);
  }
}
