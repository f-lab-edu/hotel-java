package com.hotelJava.common.error;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.OBJECT;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@JsonFormat(shape = OBJECT)
@Getter
public enum ErrorCode {
  // 회원 관련 에러
  DUPLICATED_EMAIL_FOUND(409, "Member Email already exists"),

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
