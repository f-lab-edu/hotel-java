package com.hotelJava.member.adapter.out.persistence;

import com.hotelJava.common.error.ErrorCode;
import com.hotelJava.common.error.exception.BadRequestException;
import com.hotelJava.member.application.port.out.persistence.CheckDuplicatedMemberEmailPort;
import com.hotelJava.member.application.port.out.persistence.FindMemberPort;
import com.hotelJava.member.application.port.out.persistence.RegisterMemberPort;
import com.hotelJava.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
class MemberAdapter implements CheckDuplicatedMemberEmailPort, FindMemberPort, RegisterMemberPort {

  private final MemberRepository memberRepository;

  @Override
  public Member findByEmail(String email) {
    return memberRepository
        .findByEmail(email)
        .orElseThrow(() -> new BadRequestException(ErrorCode.EMAIL_NOT_FOUND));
  }

  @Override
  public void register(Member member) {
    memberRepository.save(member);
  }

  @Override
  public boolean isDuplicated(String email) {
    return memberRepository.existsByEmail(email);
  }
}
