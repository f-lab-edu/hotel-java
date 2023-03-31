package com.hotelJava.member.service;

import static com.hotelJava.member.util.MemberMapper.MEMBER_MAPPER;

import com.hotelJava.member.domain.Member;
import com.hotelJava.member.domain.ProfileInfo;
import com.hotelJava.member.dto.SignUpRequestDto;
import com.hotelJava.member.error.exception.DuplicatedEmailException;
import com.hotelJava.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

  private final MemberRepository memberRepository;

  public void signUp(ProfileInfo profileInfo) {
    if (isDuplicatedEmail(profileInfo.getEmail())) {
      throw new DuplicatedEmailException();
    }
    Member member = MEMBER_MAPPER.toEntity((SignUpRequestDto) profileInfo);
    memberRepository.save(member);
  }

  public boolean isDuplicatedEmail(String email) {
    return memberRepository.existsByEmail(email);
  }
}
