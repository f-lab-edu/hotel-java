package com.hotelJava.member.application.port.in;

import com.hotelJava.member.domain.specification.Profile;

public interface ChangeProfileUseCase {
  void changeProfile(String email, Profile profile);
}
