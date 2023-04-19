package com.hotelJava.member;

import com.hotelJava.member.domain.Member;
import com.hotelJava.member.dto.SignUpRequestDto;

public class MemberTestFixture {
  /** test fixture */
  public static SignUpRequestDto getTestSignUpDto() {
    return SignUpRequestDto.builder()
        .email("testcode@example.com")
        .name("testcode")
        .phone("010-1111-2222")
        .password("abcd1234")
        .build();
  }

  public static Member getTestMember() {
    return Member.builder()
        .email("testcode@emxample.com")
        .name("010-1111-2222")
        .phone("010-1111-2222")
        .password("abcd1234")
        .build();
  }
}
