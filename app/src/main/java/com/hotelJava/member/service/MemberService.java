package com.hotelJava.member.service;

import static com.hotelJava.member.util.MemberMapper.MEMBER_MAPPER;

import com.hotelJava.common.error.ErrorCode;
import com.hotelJava.common.error.exception.BadRequestException;
import com.hotelJava.member.domain.Member;
import com.hotelJava.member.dto.ChangeProfileRequestDto;
import com.hotelJava.member.dto.SignUpRequestDto;
import com.hotelJava.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

  private final MemberRepository memberRepository;
  private final PasswordEncoder passwordEncoder;

  @Transactional
  public void signUp(SignUpRequestDto dto) {
    if (isDuplicatedEmail(dto.getEmail())) {
      throw new BadRequestException(ErrorCode.DUPLICATED_EMAIL_FOUND);
    }
    Member member = MEMBER_MAPPER.toEntity(dto);
    member.changePassword(encrypt(dto.getPassword()));
    memberRepository.save(member);
  }

  @Transactional
  public void changeProfile(String email, ChangeProfileRequestDto dto) {
    Member member = findByEmail(email);
    member.changeProfile(dto);
  }

  public boolean isDuplicatedEmail(String email) {
    return memberRepository.existsByEmail(email);
  }

  private Member findByEmail(String email) {
    return memberRepository
        .findByEmail(email)
        .orElseThrow(() -> new BadRequestException(ErrorCode.EMAIL_NOT_FOUND));
  }

  private String encrypt(String password) {
    return passwordEncoder.encode(password);
  }
}
