package com.hotelJava.member.application.port.in.command;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ChangePasswordCommand {
  @NotBlank(message = "비밀번호를 입력하세요.")
  private final String password;
}
