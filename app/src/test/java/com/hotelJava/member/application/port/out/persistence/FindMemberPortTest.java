package com.hotelJava.member.application.port.out.persistence;

import com.hotelJava.DomainTestFixture;
import com.hotelJava.common.error.exception.BadRequestException;
import com.hotelJava.member.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class FindMemberPortTest {
  @Autowired private FindMemberPort sut;
  @Autowired private RegisterMemberPort registerMemberPort;

  @Test
  void 회원가입_회원조회() {
    // given
    Member member = DomainTestFixture.member();

    // when
    registerMemberPort.register(member);

    // then
    Member findMember = sut.findByEmail(member.getEmail());
    Assertions.assertThat(findMember).isEqualTo(member);
  }

  @Test
  void 회원조회_실패() {
    Assertions.assertThatThrownBy(() -> sut.findByEmail("example email"))
        .isInstanceOf(BadRequestException.class);
  }
}
