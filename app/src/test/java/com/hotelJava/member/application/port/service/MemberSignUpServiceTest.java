package com.hotelJava.member.application.port.service;

import static org.mockito.Mockito.*;

import com.hotelJava.TestFixture;
import com.hotelJava.common.error.exception.BadRequestException;
import com.hotelJava.member.application.port.in.SignUpUseCase;
import com.hotelJava.member.application.port.in.command.MemberSignUpCommand;
import com.hotelJava.member.application.port.out.persistence.CheckDuplicatedMemberEmailPort;
import com.hotelJava.member.application.port.out.persistence.FindMemberPort;
import com.hotelJava.member.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
class MemberSignUpServiceTest {

  @Autowired private SignUpUseCase sut;
  @Autowired private FindMemberPort findMemberPort;
  @SpyBean private CheckDuplicatedMemberEmailPort memberEmailDuplicateCheckPort;

  @Test
  @DisplayName("같은 이메일을 사용하는 회원이 없다면 회원가입에 성공한다.")
  @Transactional
  void 회원가입성공() {
    // given
    MemberSignUpCommand signUpCommand = TestFixture.getMemberSignUpCommand();

    // when
    Member registeredMember = sut.signUp(signUpCommand);
    Member findMember = findMemberPort.findByEmail(signUpCommand.getEmail());

    // then
    Assertions.assertThat(registeredMember).isEqualTo(findMember);
  }

  @Test
  @DisplayName("같은 이메일을 사용하는 회원이 있다면 회원가입에 실패한다.")
  @Transactional
  void 회원가입실패() {
    // given
    doReturn(true).when(memberEmailDuplicateCheckPort).isDuplicated(anyString());

    // when, then
    Assertions.assertThatThrownBy(() -> sut.signUp(TestFixture.getMemberSignUpCommand()))
        .isInstanceOf(BadRequestException.class);
  }
}
