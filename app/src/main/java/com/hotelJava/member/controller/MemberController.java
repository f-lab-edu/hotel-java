package com.hotelJava.member.controller;

import com.hotelJava.common.dto.ApiResponse;
import com.hotelJava.member.dto.SignUpRequestDto;
import com.hotelJava.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/members")
public class MemberController {

  private final MemberService memberService;

  @PostMapping
  public ApiResponse<String> signUp(@RequestBody SignUpRequestDto signUpDto) {
    if (memberService.isDuplicatedEmail(signUpDto.getEmail())) {
      return ApiResponse.error("Duplicated Email");
    }

    memberService.signUp(signUpDto.toEntity());
    return ApiResponse.success();
  }
}
