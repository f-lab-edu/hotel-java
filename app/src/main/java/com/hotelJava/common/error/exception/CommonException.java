package com.hotelJava.common.error.exception;

import com.hotelJava.common.error.ErrorCode;
import com.hotelJava.common.error.ErrorResult;
import lombok.Getter;

@Getter
public class CommonException extends RuntimeException {
  private final int statusCode;

  public CommonException(ErrorCode errorCode) {
    super(errorCode.getMessage());
    this.statusCode = errorCode.getCode();
  }

  public ErrorResult errorResult() {
    return new ErrorResult(statusCode, super.getMessage());
  }
}
