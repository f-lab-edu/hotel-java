package com.hotelJava.member.application.service;

import com.hotelJava.common.error.ErrorCode;
import com.hotelJava.common.error.exception.BadRequestException;
import com.hotelJava.member.application.port.in.SignUpUseCase;
import com.hotelJava.member.application.port.in.command.MemberSignUpCommand;
import com.hotelJava.member.application.port.out.persistence.CheckDuplicatedMemberEmailPort;
import com.hotelJava.member.application.port.out.persistence.RegisterMemberPort;
import com.hotelJava.member.domain.Member;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class SignUpService implements SignUpUseCase {

  private final RegisterMemberPort registerMemberPort;
  private final CheckDuplicatedMemberEmailPort memberEmailDuplicateCheckPort;
  private final PasswordEncoder passwordEncoder;

  public Member signUp(MemberSignUpCommand command) {
    requireNotDuplicated(command.getEmail());

    Member member =
        Member.builder()
            .email(command.getEmail())
            .name(command.getName())
            .password(passwordEncoder.encode(command.getRawPassword()))
            .phone(command.getPhone())
            .build();

    registerMemberPort.register(member);

    return member;
  }

  private void requireNotDuplicated(String email) {
    if (memberEmailDuplicateCheckPort.isDuplicated(email)) {
      throw new BadRequestException(ErrorCode.DUPLICATED_EMAIL_FOUND);
    }
  }
}
