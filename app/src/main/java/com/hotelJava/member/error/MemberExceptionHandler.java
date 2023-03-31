package com.hotelJava.member.error;

import com.hotelJava.member.error.errorCode.MemberErrorCode;
import com.hotelJava.member.error.exception.DuplicatedEmailException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackages = "com.hotelJava.member")
public class MemberExceptionHandler {

  @ResponseStatus(HttpStatus.CONFLICT)
  @ExceptionHandler
  public MemberErrorCode duplicatedEmailHandle(DuplicatedEmailException ex) {
    return MemberErrorCode.DUPLICATED_EMAIL_FOUND;
  }
}
