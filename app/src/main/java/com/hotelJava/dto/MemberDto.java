package com.hotelJava.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MemberDto {
  private String email;
  private String name;
  private String password;
  private String phoneNumber;
}
