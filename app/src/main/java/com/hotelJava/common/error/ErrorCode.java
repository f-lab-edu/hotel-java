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

  // 로그인 관련 에러
  AUTHENTICATION_FAIL(401, "로그인에 실패하였습니다. 로그인을 다시 시도하세요"),
  ACCESS_DENIED(403, "접근할 수 없는 경로입니다"),

  // 숙소 관련 에러
  ACCOMMODATION_NOT_FOUND(404, "해당 숙소가 존재하지 않습니다."),
  DUPLICATED_NAME_FOUND(409, "숙소명이 이미 존재합니다."),
  NO_MINIMUM_PRICE_FOUND(500, "숙소의 최소 가격을 찾을 수 없습니다."),

  // 예약 관련 에러
  OUT_OF_STOCK(400, "재고 수량이 부족합니다"),
  OVER_MAX_OCCUPANCY(400, "객실 최대 인원을 초과하였습니다"),

  // 결제 관련 에러
  PAYMENT_FAIL(400, "결제 오류"),

  // 클라이언트 에러
  BAD_REQUEST_ERROR(400, "요청값이 잘못되었습니다"),

  // 서버 에러
  INTERNAL_SERVER_ERROR(500, "요청을 정상 처리하지 못하였습니다");

  private final int code;
  private final String message;

  ErrorCode(int code, String message) {
    this.code = code;
    this.message = message;
  }
}
