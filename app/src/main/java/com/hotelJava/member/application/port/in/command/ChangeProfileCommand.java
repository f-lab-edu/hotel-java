package com.hotelJava.member.application.port.in.command;

import com.hotelJava.member.domain.specification.Profile;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ChangeProfileCommand implements Profile {
  @NotBlank(message = "이름을 입력하세요.")
  private String name;

  @NotBlank(message = "휴대폰 번호를 입력하세요.")
  private String phone;
}
