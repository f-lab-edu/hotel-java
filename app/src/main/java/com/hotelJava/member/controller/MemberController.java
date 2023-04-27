package com.hotelJava.member.controller;

import com.hotelJava.member.dto.ChangePasswordRequestDto;
import com.hotelJava.member.dto.ChangeProfileRequestDto;
import com.hotelJava.member.dto.SignUpRequestDto;
import com.hotelJava.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
  public void signUp(@RequestBody SignUpRequestDto dto) {
    memberService.signUp(dto);
  }

  @PutMapping
  public void changeProfile(
      @AuthenticationPrincipal(expression = "email") String loginEmail,
      ChangeProfileRequestDto dto) {
    memberService.changeProfile(loginEmail, dto);
  }

  @DeleteMapping
  public void withdrawal(@AuthenticationPrincipal(expression = "email") String loginEmail) {
    memberService.withdrawal(loginEmail);
  }

  @PutMapping("/password")
  public void changePassword(
      @AuthenticationPrincipal(expression = "email") String loginEmail,
      ChangePasswordRequestDto dto) {
    memberService.changePassword(loginEmail, dto.getNewPassword());
  }
}
