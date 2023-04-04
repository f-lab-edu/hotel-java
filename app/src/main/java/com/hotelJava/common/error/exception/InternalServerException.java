package com.hotelJava.common.error.exception;

import com.hotelJava.common.error.ErrorCode;

public class InternalServerException extends CommonException {
  public InternalServerException(ErrorCode errorCode) {
    super(errorCode);
  }
}
