package com.hotelJava.member.application.port.out.persistence;

import com.hotelJava.DomainTestFixture;
import com.hotelJava.member.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("dev")
@DataJpaTest(includeFilters = @Filter(type = FilterType.ANNOTATION, classes = Repository.class))
class RegisterMemberPortTest {
  @Autowired private RegisterMemberPort sut;
  @Autowired private FindMemberPort findMemberPort;

  @Test
  void 회원가입() {
    // given
    Member member = DomainTestFixture.member();

    // when
    sut.register(member);

    // then
    Member findMember = findMemberPort.findByEmail(member.getEmail());
    Assertions.assertThat(findMember).isEqualTo(member);
  }
}
