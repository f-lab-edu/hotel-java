package com.hotelJava.member.error.errorCode;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.OBJECT;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@JsonFormat(shape = OBJECT)
@Getter
public enum MemberErrorCode {
  DUPLICATED_EMAIL_FOUND(409, "Email already exists");
  private final int code;
  private final String description;

  MemberErrorCode(int code, String description) {
    this.code = code;
    this.description = description;
  }
}
