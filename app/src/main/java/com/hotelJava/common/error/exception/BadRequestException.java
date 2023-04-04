package com.hotelJava.common.error.exception;

import com.hotelJava.common.error.ErrorCode;

public class BadRequestException extends CommonException {
  public BadRequestException(ErrorCode errorCode) {
    super(errorCode);
  }
}
