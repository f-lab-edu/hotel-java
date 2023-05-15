package com.hotelJava.member.application.port.service;

import com.hotelJava.common.error.ErrorCode;
import com.hotelJava.common.error.exception.BadRequestException;
import com.hotelJava.member.application.port.in.SignUpUseCase;
import com.hotelJava.member.application.port.in.command.MemberSignUpCommand;
import com.hotelJava.member.application.port.out.CheckDuplicatedMemberEmailPort;
import com.hotelJava.member.application.port.out.RegisterMemberPort;
import com.hotelJava.member.domain.Member;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class MemberSignUpService implements SignUpUseCase {

  private final RegisterMemberPort signUpMemberPort;
  private final CheckDuplicatedMemberEmailPort memberEmailDuplicateCheckPort;

  public Member signUp(MemberSignUpCommand command) {
    requireNotDuplicated(command.getEmail());

    Member member =
        new Member(
            command.getEmail(), command.getName(), command.getPlainPassword(), command.getPhone());

    signUpMemberPort.signUp(member);

    return member;
  }

  private void requireNotDuplicated(String email) {
    if (memberEmailDuplicateCheckPort.isDuplicated(email)) {
      throw new BadRequestException(ErrorCode.DUPLICATED_EMAIL_FOUND);
    }
  }
}
