package com.hotelJava.security.token;

import static org.assertj.core.api.Assertions.*;

import com.hotelJava.TestFixture;
import com.hotelJava.security.MemberDetails;
import com.hotelJava.security.util.impl.JwtPayload;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LoginPostAuthenticationTokenTest {

  @Test
  @DisplayName("로그인 인증을 통과한 토큰으로부터 JWT 페이로드를 추출할 수 있다")
  void getJwtPayload_JwtPayload() {

    // given
    MemberDetails memberDetails = new MemberDetails(TestFixture.getMember());
    LoginPostAuthenticationToken postAuthToken = new LoginPostAuthenticationToken(memberDetails);

    // when
    JwtPayload jwtPayload = postAuthToken.getJwtPayload();

    // then
    JwtPayload expect =
        JwtPayload.builder()
            .subject(postAuthToken.getEmail())
            .roles(postAuthToken.getRoles())
            .build();

    assertThat(jwtPayload).isEqualTo(expect);
  }
}
