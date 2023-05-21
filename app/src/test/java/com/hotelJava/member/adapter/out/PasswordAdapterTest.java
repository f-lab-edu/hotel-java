package com.hotelJava.member.adapter.out;

import static org.mockito.Mockito.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class PasswordAdapterTest {

  @Autowired private PasswordAdapter passwordAdapter;
  @SpyBean private PasswordEncoder passwordEncoder;

  @Test
  void 비밀번호_일치여부_확인() {
    // given
    String rawPassword = "rawPassword";
    String encryptedPassword = "encryptedPassword";

    // when
    Mockito.doReturn(true).when(passwordEncoder).matches(rawPassword, encryptedPassword);

    // then
    Assertions.assertThat(passwordAdapter.matches(rawPassword, encryptedPassword)).isTrue();
  }

  @Test
  void 비밀번호_암호화() {
    // given
    doReturn("encryptedPassword").when(passwordEncoder).encode(anyString());

    // when, then
    Assertions.assertThat(passwordAdapter.encode("rawPassword")).isEqualTo("encryptedPassword");
  }
}
