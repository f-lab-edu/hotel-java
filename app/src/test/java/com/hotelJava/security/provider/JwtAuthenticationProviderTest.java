package com.hotelJava.security.provider;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.github.javafaker.Faker;
import com.hotelJava.DomainTestFixture;
import com.hotelJava.security.MemberDetails;
import com.hotelJava.security.token.JwtPostAuthenticationToken;
import com.hotelJava.security.token.JwtPreAuthenticationToken;
import com.hotelJava.security.util.impl.JwtTokenDecoder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

@SpringBootTest
class JwtAuthenticationProviderTest {

  private final Faker faker = Faker.instance();

  @Autowired private JwtAuthenticationProvider provider;
  @SpyBean private JwtTokenDecoder decoder;

  @Test
  @DisplayName("token 값을 갖고 있는 JwtPreAuthenticationToken 에 대해 인증을 수행하면 JwtPostAuthenticationToken 이 생성된다")
  void authenticate() {
    // given
    String token = faker.letterify("????");
    JwtPreAuthenticationToken preAuthentication = new JwtPreAuthenticationToken(token);
    MemberDetails loginMemberDetails = new MemberDetails(DomainTestFixture.member());
    doReturn(loginMemberDetails).when(decoder).decode(anyString());

    // when
    assertThat(provider.authenticate(preAuthentication))
        .isEqualTo(new JwtPostAuthenticationToken(loginMemberDetails));
  }
}
