package com.hotelJava.member.dto;

import com.hotelJava.member.domain.Profile;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class SignUpRequestDto implements Profile {
  private String email;
  private String name;
  private String password;
  private String phone;
}
