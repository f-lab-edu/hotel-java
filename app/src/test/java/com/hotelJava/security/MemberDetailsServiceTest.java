package com.hotelJava.security;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import com.hotelJava.TestFixture;
import com.hotelJava.common.error.exception.BadRequestException;
import com.hotelJava.member.application.port.out.persistence.FindMemberPort;
import com.hotelJava.member.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.security.core.userdetails.UserDetails;

@SpringBootTest
class MemberDetailsServiceTest {

  @Autowired MemberDetailsService memberDetailsService;
  @SpyBean FindMemberPort findMemberPort;

  @Test
  @DisplayName("이메일을 바탕으로 사용자를 올바르게 인식하는지 테스트한다")
  void loadUserByUsername_MemberDetails_ValidMember() {
    // given
    Member member = TestFixture.getMember();
    doReturn(member).when(findMemberPort).findByEmail(anyString());

    // when
    UserDetails expect = new MemberDetails(member);
    UserDetails memberDetails = memberDetailsService.loadUserByUsername(member.getEmail());

    // then
    Assertions.assertThat(memberDetails).isEqualTo(expect);
  }

  @Test
  @DisplayName("등록되지 않은 이메일로 사용자를 조회하면 예외가 발생하는지 테스트한다")
  void loadUserByUsername_BadRequestException_NonExistMember() {
    assertThatThrownBy(() -> memberDetailsService.loadUserByUsername(Mockito.anyString()))
        .isInstanceOf(BadRequestException.class);
  }
}
