package com.hotelJava.member.application.port.in;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;

import com.hotelJava.CommandTestFixture;
import com.hotelJava.common.error.exception.BadRequestException;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class SignUpUseCaseTest {
  @Autowired private SignUpUseCase sut;
  @Autowired private FindMemberPort findMemberPort;
  @SpyBean private PasswordEncoder passwordEncoder;
  @SpyBean private CheckDuplicatedMemberEmailPort memberEmailDuplicateCheckPort;

  @Test
  @DisplayName("같은 이메일을 사용하는 회원이 없다면 회원가입에 성공하며 비밀번호는 암호화된다.")
  void 회원가입성공() {
    // given
    MemberSignUpCommand signUpCommand = CommandTestFixture.memberSignUpCommand();

    // when
    doReturn("encryptedPassword").when(passwordEncoder).encode(anyString());
    Member registeredMember = sut.signUp(signUpCommand);
    Member findMember = findMemberPort.findByEmail(signUpCommand.getEmail());

    // then
    assertThat(registeredMember).isEqualTo(findMember);
    Assertions.assertThat(findMember.getPassword()).isEqualTo("encryptedPassword");
  }

  @Test
  @DisplayName("같은 이메일을 사용하는 회원이 있다면 회원가입에 실패한다.")
  @Transactional
  void 회원가입실패() {
    // given
    doReturn(true).when(memberEmailDuplicateCheckPort).isDuplicated(anyString());

    // when, then
    Assertions.assertThatThrownBy(() -> sut.signUp(CommandTestFixture.memberSignUpCommand()))
        .isInstanceOf(BadRequestException.class);
  }
}
