package com.hotelJava.member.application.port.service;

import com.hotelJava.member.application.port.in.ChangePasswordUseCase;
import com.hotelJava.member.application.port.out.FindMemberPort;
import com.hotelJava.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ChangePasswordService implements ChangePasswordUseCase {

  private final FindMemberPort findMemberPort;

  @Override
  public void changePassword(String email, String password) {
    Member member = findMemberPort.findByEmail(email);
    member.changePassword(password);
  }
}
