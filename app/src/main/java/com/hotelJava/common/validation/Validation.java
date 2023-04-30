package com.hotelJava.common.validation;

import com.hotelJava.common.error.ErrorCode;
import com.hotelJava.common.error.exception.BadRequestException;
import com.hotelJava.common.util.Base32Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Validation {

  private final Base32Util base32Util;

  public Long validateIdForEmptyOrNullAndDecoding(String encodedId) {
    return base32Util.decode(encodedId).filter(id -> !id.trim().isEmpty()).stream()
        .mapToLong(Long::parseLong)
        .findFirst()
        .orElseThrow(() -> new BadRequestException(ErrorCode.BAD_REQUEST_ERROR));
  }
}
