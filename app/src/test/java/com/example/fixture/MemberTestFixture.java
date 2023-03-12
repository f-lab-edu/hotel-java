package com.example.fixture;

import com.example.dto.MemberDto;

public class MemberTestFixture {
    public static final MemberDto MEMBER_SIGNUP_REQUEST = MemberDto.builder()
            .email("memberA@gmail.com")
            .name("memberA")
            .password("aAbBcC!123")
            .phoneNumber("010-1111-1111")
            .build();
}
