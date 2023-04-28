package com.hotelJava.member.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChangePasswordRequestDto {
  private String newPassword;
}
