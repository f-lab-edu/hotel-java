package com.hotelJava.common.util;

import com.hotelJava.common.dto.DecodeId;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class IdDecodedConverter implements Converter<String, DecodeId> {

  private final Base32Util base32Util;

  @Override
  public DecodeId convert(@NotNull String encodedId) {
    String decodedId =
        base32Util.decode(encodedId).orElseThrow(() -> new IllegalArgumentException("잘못된 입력값입니다."));

    return new DecodeId(Long.parseLong(decodedId));
  }
}
