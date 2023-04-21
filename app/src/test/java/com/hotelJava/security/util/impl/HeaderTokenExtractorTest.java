package com.hotelJava.security.util.impl;

import static org.assertj.core.api.Assertions.*;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HeaderTokenExtractorTest {

  private final HeaderTokenExtractor extractor = new HeaderTokenExtractor();

  @Test
  @DisplayName("Authorization Header 에서 토큰값만 추출한다")
  void extract_tokenValue() {
    // given
    String token = Faker.instance().letterify("????????");
    String header = HeaderTokenExtractor.TOKEN_PREFIX + token;
    
    // when, then
    assertThat(extractor.extract(header)).isEqualTo(token);
  }
}
