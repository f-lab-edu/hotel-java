package com.hotelJava.member.dto;

import com.hotelJava.member.domain.ProfileInfo;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignUpRequestDto implements ProfileInfo {
  private String email;
  private String name;
  private String password;
  private String phone;
}
