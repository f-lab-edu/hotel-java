package com.hotelJava.common.error.handler;

import com.hotelJava.common.error.ErrorResult;
import com.hotelJava.common.error.exception.CommonException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(annotations = RestController.class)
public class CommonExceptionHandler {

  @ExceptionHandler
  public ResponseEntity<ErrorResult> commonExceptionHandle(CommonException ex) {
    return ResponseEntity.status(ex.getStatusCode()).body(ex.errorResult());
  }
}
