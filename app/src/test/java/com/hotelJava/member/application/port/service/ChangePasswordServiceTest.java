package com.hotelJava.member.application.port.service;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;

import com.github.javafaker.Faker;
import com.hotelJava.TestFixture;
import com.hotelJava.member.application.port.in.ChangePasswordUseCase;
import com.hotelJava.member.application.port.out.EncryptPasswordPort;
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
class ChangePasswordServiceTest {

  @Autowired private ChangePasswordUseCase changePasswordUseCase;
  @SpyBean private FindMemberPort findMemberPort;
  @SpyBean private EncryptPasswordPort encryptPasswordPort;

  @Test
  @DisplayName("주어진 비밀번호로 변경하였을 때, 비밀번호가 변경됐는지 확인한다.")
  void 비밀번호변경() {
    // given
    Member member = TestFixture.getMember();
    String newPassword = Faker.instance().internet().password();
    doReturn(member).when(findMemberPort).findByEmail(member.getEmail());

    // when
    doReturn("encryptedPassword").when(encryptPasswordPort).encode(anyString());
    changePasswordUseCase.changePassword(member.getEmail(), newPassword);

    // then
    Member findMember = findMemberPort.findByEmail(member.getEmail());
    Assertions.assertThat(findMember.getPassword()).isEqualTo("encryptedPassword");
  }
}
