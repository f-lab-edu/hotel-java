package com.hotelJava.member.service;

import static com.hotelJava.member.util.MemberMapper.MEMBER_MAPPER;

import com.hotelJava.common.error.ErrorCode;
import com.hotelJava.common.error.exception.BadRequestException;
import com.hotelJava.member.domain.Member;
import com.hotelJava.member.dto.SignUpRequestDto;
import com.hotelJava.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

  private final MemberRepository memberRepository;
  private final PasswordEncoder passwordEncoder;

  public void signUp(SignUpRequestDto dto) {
    if (isDuplicatedEmail(dto.getEmail())) {
      throw new BadRequestException(ErrorCode.DUPLICATED_EMAIL_FOUND);
    }
    Member member = MEMBER_MAPPER.toEntity(dto);
    member.changePassword(encrypt(dto.getPassword()));
    memberRepository.save(member);
  }

  public boolean isDuplicatedEmail(String email) {
    return memberRepository.existsByEmail(email);
  }

  private String encrypt(String password) {
    return passwordEncoder.encode(password);
  }
}
