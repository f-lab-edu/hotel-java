package com.hotelJava.member.application.port.in;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

import com.hotelJava.CommandTestFixture;
import com.hotelJava.DomainTestFixture;
import com.hotelJava.common.error.exception.BadRequestException;
import com.hotelJava.member.application.port.out.persistence.FindMemberPort;
import com.hotelJava.member.domain.Member;
import com.hotelJava.member.domain.specification.Profile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

@SpringBootTest
class ChangeProfileUseCaseTest {

  @Autowired private ChangeProfileUseCase sut;
  @SpyBean private FindMemberPort findMemberPort;

  @Test
  @DisplayName("프로필 변경을 하면 회원의 프로필(이름, 핸드폰번호) 이 변경된다.")
  void 프로필변경() {
    // given
    Member member = DomainTestFixture.member();
    Profile profile = CommandTestFixture.changeProfileCommand();
    doReturn(member).when(findMemberPort).findByEmail(member.getEmail());

    // when
    sut.changeProfile(member.getEmail(), profile);

    // then
    Member findMember = findMemberPort.findByEmail(member.getEmail());
    assertThat(profile).usingRecursiveComparison().isEqualTo(findMember);
  }

  @Test
  @DisplayName("존재하지 않는 회원의 프로필 변경을 시도하면 예외가 발생한다")
  void 프로필변경_존재하지않는회원() {
    // given
    Profile profile = CommandTestFixture.changeProfileCommand();

    // when, then
    assertThatThrownBy(() -> sut.changeProfile("email example", profile))
        .isInstanceOf(BadRequestException.class);
  }
}
