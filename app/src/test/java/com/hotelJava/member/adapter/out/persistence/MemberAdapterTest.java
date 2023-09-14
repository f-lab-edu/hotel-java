package com.hotelJava.member.adapter.out.persistence;

import com.hotelJava.TestFixture;
import com.hotelJava.common.error.exception.BadRequestException;
import com.hotelJava.member.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
@Import(MemberAdapter.class)
class MemberAdapterTest {

  @Autowired private MemberAdapter stu;

  @Test
  void 회원가입_회원조회() {
    // given
    Member member = TestFixture.getMember();

    // when
    stu.register(member);

    // then
    Member findMember = stu.findByEmail(member.getEmail());
    Assertions.assertThat(findMember).isEqualTo(member);
  }

  @Test
  void 회원조회_실패() {
    Assertions.assertThatThrownBy(() -> stu.findByEmail("example email"))
        .isInstanceOf(BadRequestException.class);
  }

  @Test
  void 중복검사() {
    // given
    Member member = TestFixture.getMember();

    // when
    stu.register(member);

    // then
    Assertions.assertThat(stu.isDuplicated(member.getEmail())).isTrue();
  }
}
