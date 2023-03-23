package com.hotelJava.member.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.hotelJava.member.domain.Grade;
import com.hotelJava.member.domain.Member;
import com.hotelJava.member.domain.Role;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class MemberServiceTest {

  @Autowired private MemberService memberService;

  @Test
  @DisplayName("회원가입 기능을 테스트한다.")
  @Transactional
  void signUp() {
    // when
    memberService.signUp(TEST_MEMBER);
    Member findMember = memberService.find(TEST_MEMBER);

    // then
    assertThat(TEST_MEMBER).isEqualTo(findMember);
  }

  @Test
  @DisplayName("이메일 중복 검사 기능을 테스트한다.")
  @Transactional
  void isDuplicated() {
    // given
    memberService.signUp(TEST_MEMBER);

    // when
    boolean result = memberService.isDuplicatedEmail(TEST_MEMBER.getEmail());

    // then
    Assertions.assertThat(result).isTrue();
  }

  /** test fixture */
  static final Member TEST_MEMBER =
      Member.builder()
          .id(1L)
          .email("testcode@example.com")
          .name("testcode")
          .phone("010-1111-2222")
          .password("abcd1234")
          .grade(Grade.NORMAL)
          .role(Role.USER)
          .build();
}
