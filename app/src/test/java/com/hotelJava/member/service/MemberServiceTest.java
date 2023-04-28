package com.hotelJava.member.service;

import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.Mockito.*;

import com.github.javafaker.Faker;
import com.hotelJava.common.error.exception.BadRequestException;
import com.hotelJava.member.MemberTestFixture;
import com.hotelJava.member.domain.Member;
import com.hotelJava.member.dto.ChangeProfileRequestDto;
import com.hotelJava.member.dto.SignUpRequestDto;
import com.hotelJava.member.repository.MemberRepository;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@SpringBootTest
@Transactional
class MemberServiceTest {

  @Autowired private MemberService memberService;
  @SpyBean private MemberRepository memberRepository;

  @Test
  @DisplayName("회원가입 기능을 테스트한다.")
  void signUp() {
    // given
    SignUpRequestDto signUpDto = MemberTestFixture.getSignUpDto();

    // when
    memberService.signUp(signUpDto);
    Member findMember =
        memberRepository
            .findByEmail(signUpDto.getEmail())
            .orElseThrow(IllegalArgumentException::new);

    // then
    assertSoftly(
        softly -> {
          softly.assertThat(findMember.getEmail()).isEqualTo(signUpDto.getEmail());
          softly.assertThat(findMember.getName()).isEqualTo(signUpDto.getName());
          softly.assertThat(findMember.getPhone()).isEqualTo(signUpDto.getPhone());
        });
  }

  @Test
  @DisplayName("프로필 변경 기능을 테스트한다.")
  void changeProfile() {
    // given
    Member member = MemberTestFixture.getMember();
    ChangeProfileRequestDto newProfile = MemberTestFixture.getChangeProfileDto();
    doReturn(Optional.of(member)).when(memberRepository).findByEmail(member.getEmail());

    // when
    memberService.changeProfile(member.getEmail(), newProfile);

    // then
    Member findMember =
        memberRepository.findByEmail(member.getEmail()).orElseThrow(IllegalArgumentException::new);
    assertSoftly(
        softly -> {
          softly.assertThat(findMember.getName()).isEqualTo(newProfile.getName());
          softly.assertThat(findMember.getPhone()).isEqualTo(newProfile.getPhone());
        });
  }

  @Test
  @DisplayName("회원탈퇴 기능을 테스트한다.")
  void withdrawal() {
    // given
    Member member = MemberTestFixture.getMember();
    doReturn(Optional.of(member)).when(memberRepository).findByEmail(member.getEmail());

    // when
    memberService.withdrawal(member.getEmail());

    // then
    Member findMember =
        memberRepository.findByEmail(member.getEmail()).orElseThrow(IllegalArgumentException::new);
    Assertions.assertThat(findMember.isDeleted()).isTrue();
  }

  @Test
  @DisplayName("이미 탈퇴한 회원을 탈퇴시키는 경우 예외가 발생하는지 테스트한다.")
  void withdrawal_BadRequestException_alreadyDeleted() {
    // given
    Member member = MemberTestFixture.getMember();
    member.deleteAccount();

    // when, then
    Assertions.assertThatThrownBy(() -> memberService.withdrawal(member.getEmail()))
        .isInstanceOf(BadRequestException.class);
  }

  @Test
  @DisplayName("비밀빈호 변경 기능을 테스트한다.")
  void changePassword() {
    // given
    Member member = MemberTestFixture.getMember();
    String newPassword = Faker.instance().internet().password();
    doReturn(Optional.of(member)).when(memberRepository).findByEmail(member.getEmail());

    // when
    memberService.changePassword(member.getEmail(), newPassword);

    // then
    Member findMember =
        memberRepository.findByEmail(member.getEmail()).orElseThrow(IllegalArgumentException::new);
    Assertions.assertThat(findMember.getPassword()).isEqualTo(newPassword);
  }

  @Test
  @DisplayName("이메일 중복 검사 기능을 테스트한다.")
  void isDuplicated() {
    // given
    Member member = MemberTestFixture.getMember();
    memberRepository.save(member);

    // when
    boolean result = memberService.isDuplicatedEmail(member.getEmail());

    // then
    Assertions.assertThat(result).isTrue();
  }
}
