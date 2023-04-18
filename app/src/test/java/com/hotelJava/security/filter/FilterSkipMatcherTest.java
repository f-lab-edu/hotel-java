package com.hotelJava.security.filter;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

class FilterSkipMatcherTest {

  private final Faker faker = new Faker();

  @Test
  @DisplayName("skip 경로가 아니고 default 경로인 요청이 매개변수로 주어진다면 결과는 true 이다")
  void matches_true() {
    // given
    String defaultPath = faker.letterify("/?????");
    String skipPath = faker.letterify("/?????");

    // when
    FilterSkipMatcher matcher =
        new FilterSkipMatcher(defaultPath, AntPathRequestMatcher.antMatcher(skipPath));
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.setServletPath(defaultPath);

    // then
    assertThat(matcher.matches(request)).isTrue();
  }

  @Test
  @DisplayName("skip 경로인 요청이 매개변수로 주어진다면 결과는 false 이다")
  void matches_false() {
    // given
    String defaultPath = faker.letterify("/?????");
    String skipPath = faker.letterify("/?????");

    // when
    FilterSkipMatcher matcher =
            new FilterSkipMatcher(defaultPath, AntPathRequestMatcher.antMatcher(skipPath));
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.setServletPath(skipPath);

    // then
    assertThat(matcher.matches(request)).isFalse();
  }
}
