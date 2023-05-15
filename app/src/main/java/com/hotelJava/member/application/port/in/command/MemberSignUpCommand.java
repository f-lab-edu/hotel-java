package com.hotelJava.member.application.port.in.command;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hotelJava.member.domain.specification.Profile;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MemberSignUpCommand implements Profile {
  @Email(message = "이메일 형식을 입력하세요.")
  private String email;

  @NotNull(message = "비밀번호를 입력하세요.")
  @JsonProperty("password")
  private String plainPassword;

  @NotBlank(message = "이름을 입력하세요.")
  private String name;

  @NotBlank(message = "휴대폰번호를 입력하세요.")
  private String phone;
}
