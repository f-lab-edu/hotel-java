package com.hotelJava.member.service;

import static com.hotelJava.member.MemberTestFixture.getMember;
import static com.hotelJava.member.MemberTestFixture.getSignUpDto;

import com.hotelJava.member.domain.Member;
import com.hotelJava.member.dto.SignUpRequestDto;
import com.hotelJava.member.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@SpringBootTest
@Transactional
class MemberServiceTest {

  @Autowired private MemberService memberService;
  @Autowired private MemberRepository memberRepository;

  @Test
  @DisplayName("회원가입 기능을 테스트한다.")
  void signUp() {
    // given
    SignUpRequestDto signUpDto = getSignUpDto();

    // when
    memberService.signUp(signUpDto);
    Member findMember = memberRepository.findByEmail(signUpDto.getEmail()).orElse(null);

    // then
    SoftAssertions.assertSoftly(
        softly -> {
          softly.assertThat(findMember.getEmail()).isEqualTo(signUpDto.getEmail());
          softly.assertThat(findMember.getName()).isEqualTo(signUpDto.getName());
          softly.assertThat(findMember.getPhone()).isEqualTo(signUpDto.getPhone());
        });
  }

  @Test
  @DisplayName("이메일 중복 검사 기능을 테스트한다.")
  void isDuplicated() {
    // given
    Member member = getMember();
    memberRepository.save(member);

    // when
    boolean result = memberService.isDuplicatedEmail(member.getEmail());

    // then
    Assertions.assertThat(result).isTrue();
  }
}
