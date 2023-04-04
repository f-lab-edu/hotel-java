package com.hotelJava.common.error;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.OBJECT;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@JsonFormat(shape = OBJECT)
@Getter
public enum ErrorCode {
  // 회원 관련 에러
  LOGIN_FAIL(401, "로그인 실패. 이메일 또는 아이디를 확인하세요"),
  DUPLICATED_EMAIL_FOUND(401, "중복된 이메일입니다"),
  DISABLED_ACCOUNT(401, "이미 탈퇴한 회원입니다"),
  LOCKED_ACCOUNT(401, "휴면 계정입니다. 비밀번호 재설정이 완료되면 휴면상태가 해제됩니다"),
  EXPIRED_PASSWORD(401, "비밀번호 재설정 기간이 지났습니다. 비밀번호를 재설정하세요"),

  // 숙소 관련 에러,
  DUPLICATED_NAME_FOUND(409, "Name already exists"),
  NO_MINIMUM_PRICE_FOUND(500, "No minimum price found for the given accommodation");

  private final int code;
  private final String message;

  ErrorCode(int code, String message) {
    this.code = code;
    this.message = message;
  }
}
