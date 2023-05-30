package com.hotelJava.member.application.port.out.persistence;

import com.hotelJava.DomainTestFixture;
import com.hotelJava.member.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CheckDuplicatedMemberEmailPortTest {

  @Autowired private CheckDuplicatedMemberEmailPort sut;
  @Autowired private RegisterMemberPort registerMemberPort;

  @Test
  void 중복검사() {
    // given
    Member member = DomainTestFixture.member();

    // when
    registerMemberPort.register(member);

    // then
    Assertions.assertThat(sut.isDuplicated(member.getEmail())).isTrue();
  }
}
