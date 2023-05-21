package com.hotelJava.member.adapter.in.web;

import com.hotelJava.member.application.port.in.ChangePasswordUseCase;
import com.hotelJava.member.application.port.in.ChangeProfileUseCase;
import com.hotelJava.member.application.port.in.SignUpUseCase;
import com.hotelJava.member.application.port.in.WithdrawalAccountUseCase;
import com.hotelJava.member.application.port.in.command.ChangePasswordCommand;
import com.hotelJava.member.application.port.in.command.ChangeProfileCommand;
import com.hotelJava.member.application.port.in.command.MemberSignUpCommand;
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

  private final SignUpUseCase memberSignUpUseCase;
  private final ChangePasswordUseCase changePasswordUseCase;
  private final ChangeProfileUseCase changeProfileUseCase;
  private final WithdrawalAccountUseCase withdrawalAccountUseCase;

  @PostMapping
  public void signUp(@RequestBody MemberSignUpCommand command) {
    memberSignUpUseCase.signUp(command);
  }

  @PutMapping("/password")
  public void changePassword(
      @AuthenticationPrincipal(expression = "email") String loginEmail,
      @RequestBody ChangePasswordCommand command) {
    changePasswordUseCase.changePassword(loginEmail, command.getPassword());
  }

  @PutMapping
  public void changeProfile(
      @AuthenticationPrincipal(expression = "email") String loginEmail,
      @RequestBody ChangeProfileCommand command) {
    changeProfileUseCase.changeProfile(loginEmail, command);
  }

  @DeleteMapping
  public void withdrawal(@AuthenticationPrincipal(expression = "email") String loginEmail) {
    withdrawalAccountUseCase.withdrawal(loginEmail);
  }
}
