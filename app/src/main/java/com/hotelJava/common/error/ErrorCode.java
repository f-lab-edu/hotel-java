package com.hotelJava.common.error;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.OBJECT;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@JsonFormat(shape = OBJECT)
@Getter
public enum ErrorCode {
  // 회원 관련 에러
  DISABLED_ACCOUNT(401, "이미 탈퇴한 회원입니다"),
  LOCKED_ACCOUNT(401, "휴면 계정입니다. 비밀번호 재설정이 완료되면 휴면상태가 해제됩니다"),
  EXPIRED_PASSWORD(401, "비밀번호 재설정 기간이 지났습니다. 비밀번호를 재설정하세요"),
  WRONG_PASSWORD(401, "로그인 실패. 비밀번호를 확인하세요"),
  DUPLICATED_EMAIL_FOUND(401, "중복된 이메일입니다"),
  EMAIL_NOT_FOUND(404, "존재하지 않는 회원입니다. 이메일을 확인하세요"),

  // 서버 내부 관련 에러
  LOGIN_TOKEN_ERROR(500, "로그인 토큰 생성 과정에서 문제가 발생하였습니다. 잠시 후 다시 시도해주세요"),
  AUTHENTICATION_FAIL(401, "로그인에 실패하였습니다. 로그인을 다시 시도하세요"),
  BAD_CREDENTIAL(401, "비정상 로그인 요청입니다. 로그인 정보를 확인해주세요"),
  ACCESS_DENIED(403, "접근할 수 없는 경로입니다"),

  // 숙소 관련 에러
  DUPLICATED_NAME_FOUND(409, "Name already exists"),
  NO_MINIMUM_PRICE_FOUND(500, "No minimum price found for the given accommodation");

  private final int code;
  private final String message;

  ErrorCode(int code, String message) {
    this.code = code;
    this.message = message;
  }
}
