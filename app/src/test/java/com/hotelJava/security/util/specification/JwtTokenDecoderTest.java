package com.hotelJava.security.util.specification;

import com.hotelJava.common.error.exception.BadRequestException;
import com.hotelJava.TestFixture;
import com.hotelJava.member.domain.Member;
import com.hotelJava.security.MemberDetails;
import com.hotelJava.security.util.impl.JwtPayload;
import com.hotelJava.security.util.impl.JwtTokenDecoder;
import com.hotelJava.security.util.impl.JwtTokenFactory;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class JwtTokenDecoderTest {

  @Autowired private JwtTokenDecoder jwtTokenDecoder;
  @Autowired private JwtTokenFactory jwtTokenFactory;

  @Test
  @DisplayName("유효한 토큰은 디코딩했을 때 MemberDetails 정보를 가져올 수 있다")
  void verify_DecodedJWT() {
    // given
    Member member = TestFixture.getMember();
    String token = getJwtToken(member, System.currentTimeMillis());

    // when
    MemberDetails memberDetails = jwtTokenDecoder.decode(token);

    // then
    Assertions.assertThat(new MemberDetails(member)).isEqualTo(memberDetails);
  }

  @Test
  @DisplayName("유효 시간이 지난 토큰은 디코딩했을 때 예외가 발생한다")
  void verify_Exception() {
    // given
    Member member = TestFixture.getMember();
    String token = getJwtToken(member, 1000);

    // when, then
    Assertions.assertThatThrownBy(() -> jwtTokenDecoder.decode(token))
        .isInstanceOf(BadRequestException.class);
  }

  private String getJwtToken(Member member, long currentTime) {
    JwtPayload payload =
        JwtPayload.builder()
            .subject(member.getEmail())
            .roles(List.of(member.getRole().name()))
            .build();
    return jwtTokenFactory.generate(payload, currentTime);
  }
}
