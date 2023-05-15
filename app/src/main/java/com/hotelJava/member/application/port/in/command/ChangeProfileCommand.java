package com.hotelJava.member.application.port.in.command;

import com.hotelJava.member.domain.specification.Profile;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ChangeProfileCommand implements Profile {
  private String name;
  private String phone;
}
