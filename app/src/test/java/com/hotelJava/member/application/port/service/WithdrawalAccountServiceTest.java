package com.hotelJava.member.application.port.service;

import static org.mockito.Mockito.doReturn;

import com.hotelJava.TestFixture;
import com.hotelJava.common.error.exception.BadRequestException;
import com.hotelJava.member.application.port.in.WithdrawalAccountUseCase;
import com.hotelJava.member.application.port.out.persistence.FindMemberPort;
import com.hotelJava.member.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class WithdrawalAccountServiceTest {

  @Autowired private WithdrawalAccountUseCase sut;
  @SpyBean private FindMemberPort findMemberPort;

  @Test
  @DisplayName("회원을 탈퇴했을 경우, isDeleted() 결과값은 true 이어야한다.")
  void 회원탈퇴_성공() {
    // given
    Member member = TestFixture.getMember();
    doReturn(member).when(findMemberPort).findByEmail(member.getEmail());

    // when
    sut.withdrawal(member.getEmail());

    // then
    Member findMember = findMemberPort.findByEmail(member.getEmail());
    Assertions.assertThat(findMember.isDeleted()).isTrue();
  }

  @Test
  @DisplayName("회원탈퇴 기능을 테스트한다.")
  void 회원탈퇴_존재하지않는회원() {
    Assertions.assertThatThrownBy(() -> sut.withdrawal("email example"))
        .isInstanceOf(BadRequestException.class);
  }

  @Test
  @DisplayName("이미 탈퇴한 회원을 탈퇴시키는 경우 예외가 발생한다.")
  void 회원탈퇴_이미탈퇴한회원() {
    // given
    Member member = TestFixture.getMember();
    member.deleteAccount();
    doReturn(member).when(findMemberPort).findByEmail(member.getEmail());

    // when, then
    Assertions.assertThatThrownBy(() -> sut.withdrawal(member.getEmail()))
        .isInstanceOf(BadRequestException.class);
  }
}
