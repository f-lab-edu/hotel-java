package com.hotelJava.security.token;

import com.hotelJava.security.MemberDetails;
import com.hotelJava.security.config.JwtConfig;
import com.hotelJava.security.util.specification.JwtPayload;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class PostAuthenticationToken extends UsernamePasswordAuthenticationToken {

  protected PostAuthenticationToken(
      Object principal, String credential, Collection<? extends GrantedAuthority> authorities) {
    super(principal, credential, authorities);
  }

  public static PostAuthenticationToken generate(MemberDetails memberDetails) {
    return new PostAuthenticationToken(
        memberDetails, memberDetails.getPassword(), memberDetails.getAuthorities());
  }

  public JwtPayload getJwtPayload(Date expireTime) {
    return JwtPayload.builder()
        .issuer(JwtConfig.ISSUER)
        .expired(expireTime)
        .subject(super.getName())
        .claims(Map.of("role", super.getAuthorities()))
        .build();
  }
}
