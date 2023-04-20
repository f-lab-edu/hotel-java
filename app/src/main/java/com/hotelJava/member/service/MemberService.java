package com.hotelJava.member.service;

import static com.hotelJava.member.util.MemberMapper.MEMBER_MAPPER;

import com.hotelJava.common.error.ErrorCode;
import com.hotelJava.common.error.exception.BadRequestException;
import com.hotelJava.member.domain.Member;
import com.hotelJava.member.domain.ProfileInfo;
import com.hotelJava.member.dto.SignUpRequestDto;
import com.hotelJava.member.repository.MemberRepository;
import com.hotelJava.security.util.specification.MemberPasswordEncoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

  private final MemberRepository memberRepository;
  private final MemberPasswordEncoder passwordEncoder;

  public void signUp(ProfileInfo profileInfo) {
    if (isDuplicatedEmail(profileInfo.getEmail())) {
      throw new BadRequestException(ErrorCode.DUPLICATED_EMAIL_FOUND);
    }
    SignUpRequestDto encrypted = passwordEncoder.encrypt((SignUpRequestDto) profileInfo);
    Member member = MEMBER_MAPPER.toEntity(encrypted);
    memberRepository.save(member);
  }

  public boolean isDuplicatedEmail(String email) {
    return memberRepository.existsByEmail(email);
  }
}
