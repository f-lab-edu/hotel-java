package com.hotelJava.security.util.impl;

import com.hotelJava.common.error.ErrorCode;
import com.hotelJava.common.error.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class HeaderTokenExtractor {
  public static final String TOKEN_PREFIX = "Bearer ";

  public String extract(String header) {
    if (header == null || header.length() < TOKEN_PREFIX.length()) {
      log.error("token extract fail");
      throw new BadRequestException(ErrorCode.AUTHENTICATION_FAIL);
    }
    return header.substring(TOKEN_PREFIX.length());
  }
}
