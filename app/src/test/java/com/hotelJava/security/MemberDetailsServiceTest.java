package com.hotelJava.security;

import static com.hotelJava.member.MemberTestFixture.getTestMember;
import static com.hotelJava.security.MemberDetails.parseAuthorities;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.junit.jupiter.api.Assertions.*;

import com.hotelJava.common.error.ErrorCode;
import com.hotelJava.common.error.exception.BadRequestException;
import com.hotelJava.common.error.exception.CommonException;
import com.hotelJava.member.domain.Member;
import com.hotelJava.member.repository.MemberRepository;
import java.util.Collection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@SpringBootTest
class MemberDetailsServiceTest {

  @Autowired MemberDetailsService memberDetailsService;
  @Autowired MemberRepository memberRepository;

  @SuppressWarnings("unchecked")
  @Test
  @DisplayName("이메일을 바탕으로 사용자를 올바르게 인식하는지 테스트한다")
  void loadUserByUsername_MemberDetails_ValidMember() {
    // given
    Member member = getTestMember();
    memberRepository.save(member);

    // when
    UserDetails memberDetails = memberDetailsService.loadUserByUsername(member.getEmail());

    // then
    assertThat(memberDetails.getUsername()).isEqualTo(member.getEmail());
    assertSoftly(
        softly -> {
          softly.assertThat(memberDetails.getUsername()).isEqualTo(member.getEmail());
          softly.assertThat(memberDetails.getPassword()).isEqualTo(member.getPassword());
          softly
              .assertThat((Collection<SimpleGrantedAuthority>) memberDetails.getAuthorities())
              .hasSameElementsAs(parseAuthorities(member.getRole()));
        });
  }

  @Test
  @DisplayName("등록되지 않은 이메일로 사용자를 조회하면 예외가 발생하는지 테스트한다")
  void loadUserByUsername_BadRequestException_NonExistMember() {
    // given
    String email = "example@test.com";

    // when, then
    CommonException exception =
        assertThrows(
            BadRequestException.class, () -> memberDetailsService.loadUserByUsername(email));

    assertThat(exception.getMessage()).isEqualTo(ErrorCode.EMAIL_NOT_FOUND.getMessage());
  }
}
