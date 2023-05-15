package com.hotelJava.member.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class PasswordTest {

  @Test
  void 비밀번호_일치() {

    // given
    Password password = Password.of("test password");

    // when, then
    Assertions.assertThat(password.matches("test password")).isTrue();
  }

  @Test
  void 비밀번호_불일치() {

    // given
    Password password = Password.of("test password");

    // when, then
    Assertions.assertThat(password.matches("wrong password")).isFalse();
  }
}
