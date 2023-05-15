package com.hotelJava.member.application.port.service;

import com.hotelJava.member.application.port.in.ChangeProfileUseCase;
import com.hotelJava.member.application.port.out.FindMemberPort;
import com.hotelJava.member.domain.Member;
import com.hotelJava.member.domain.specification.Profile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ChangeProfileService implements ChangeProfileUseCase {

  private final FindMemberPort findMemberPort;

  @Override
  public void changeProfile(String email, Profile profile) {
    Member member = findMemberPort.findByEmail(email);
    member.changeProfile(profile);
  }
}
