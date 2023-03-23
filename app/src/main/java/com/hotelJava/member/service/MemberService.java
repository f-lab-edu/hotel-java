package com.hotelJava.member.service;

import com.hotelJava.member.domain.Member;
import com.hotelJava.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

  private final MemberRepository memberRepository;

  public Member find(Member member) {
    return memberRepository.findOne(member);
  }

  public void signUp(Member member) {
    memberRepository.save(member);
  }

  public boolean isDuplicatedEmail(String email) {
    return memberRepository.findByEmail(email) != null;
  }
}
