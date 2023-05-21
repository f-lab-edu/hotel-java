package com.hotelJava.member.application.port.service;

import com.hotelJava.common.error.ErrorCode;
import com.hotelJava.common.error.exception.BadRequestException;
import com.hotelJava.member.application.port.in.WithdrawalAccountUseCase;
import com.hotelJava.member.application.port.out.persistence.FindMemberPort;
import com.hotelJava.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class WithdrawalAccountService implements WithdrawalAccountUseCase {

  private final FindMemberPort findMemberPort;

  @Override
  public void withdrawal(String email) {
    Member member = findMemberPort.findByEmail(email);
    requireNotDeletedAccount(member);
    member.deleteAccount();
  }

  private static void requireNotDeletedAccount(Member member) {
    if (member.isDeleted()) {
      throw new BadRequestException(ErrorCode.DISABLED_ACCOUNT);
    }
  }
}
