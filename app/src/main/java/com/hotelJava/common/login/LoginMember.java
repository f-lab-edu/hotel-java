package com.hotelJava.common.login;

import com.hotelJava.member.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginMember {
  private String email;
  private Role role;
}
