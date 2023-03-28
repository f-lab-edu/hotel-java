package com.hotelJava.member.service;

import com.hotelJava.member.domain.Member;
import com.hotelJava.member.error.exception.DuplicatedEmailException;
import com.hotelJava.member.error.exception.NotFoundException;
import com.hotelJava.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

  private final MemberRepository memberRepository;

  public Member find(Member member) {
    return memberRepository.findById(member.getId()).orElseThrow(NotFoundException::new);
  }

  public void signUp(Member member) {
    if (isDuplicatedEmail(member.getEmail())) {
      throw new DuplicatedEmailException();
    }
    memberRepository.save(member);
  }

  public boolean isDuplicatedEmail(String email) {
    return memberRepository.existsByEmail(email);
  }
}
