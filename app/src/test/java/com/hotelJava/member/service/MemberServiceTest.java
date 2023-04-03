package com.hotelJava.member.service;

import static com.hotelJava.member.util.MemberMapper.MEMBER_MAPPER;
import static org.assertj.core.api.Assertions.assertThat;

import com.hotelJava.member.domain.Member;
import com.hotelJava.member.dto.SignUpRequestDto;
import com.hotelJava.member.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
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
    SignUpRequestDto signUpDto = getTestSignUpDto();

    // when
    memberService.signUp(signUpDto);
    Member findMember = memberRepository.findByEmail(signUpDto.getEmail()).orElse(null);

    // then
    assertThat(MEMBER_MAPPER.toSignUpRequestDto(findMember)).isEqualTo(signUpDto);
  }

  @Test
  @DisplayName("이메일 중복 검사 기능을 테스트한다.")
  void isDuplicated() {
    // given
    Member member = getTestMember();
    memberRepository.save(member);

    // when
    boolean result = memberService.isDuplicatedEmail(member.getEmail());

    // then
    Assertions.assertThat(result).isTrue();
  }

  /** test fixture */
  static SignUpRequestDto getTestSignUpDto() {
    return SignUpRequestDto.builder()
        .email("testcode@example.com")
        .name("testcode")
        .phone("010-1111-2222")
        .password("abcd1234")
        .build();
  }

  static Member getTestMember() {
    return Member.builder()
        .email("testcode@emxample.com")
        .name("010-1111-2222")
        .phone("010-1111-2222")
        .password("abcd1234")
        .build();
  }
}