package com.hotelJava.member.dto;

import com.hotelJava.member.domain.Profile;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChangeProfileRequestDto implements Profile {
  private String name;
  private String phone;
}
