package com.example.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MemberResponseDto {
    private Long id;
    private String email;
    private String name;
    private String password;
    private String phoneNumber;
}
