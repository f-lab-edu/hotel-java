package com.hotelJava.member.application.port.in;

import com.hotelJava.member.application.port.in.command.MemberSignUpCommand;
import com.hotelJava.member.domain.Member;

public interface SignUpUseCase {
  Member signUp(MemberSignUpCommand command);
}
