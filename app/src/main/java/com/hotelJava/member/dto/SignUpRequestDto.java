package com.hotelJava.member.dto;

import com.hotelJava.member.domain.Member;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignUpRequestDto {
  private String email;
  private String name;
  private String password;
  private String phone;

  public Member toEntity() {
    return Member.builder().email(email).name(name).password(password).phone(phone).build();
  }
}
