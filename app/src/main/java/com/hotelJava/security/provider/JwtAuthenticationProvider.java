package com.hotelJava.security.provider;

import com.hotelJava.security.MemberDetails;
import com.hotelJava.security.token.JwtPostAuthenticationToken;
import com.hotelJava.security.token.JwtPreAuthenticationToken;
import com.hotelJava.security.util.impl.JwtTokenDecoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationProvider implements AuthenticationProvider {

  private final JwtTokenDecoder jwtTokenDecoder;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    JwtPreAuthenticationToken preAuthToken = (JwtPreAuthenticationToken) authentication;

    String token = (String) preAuthToken.getPrincipal();

    MemberDetails memberDetails = jwtTokenDecoder.decode(token);

    return new JwtPostAuthenticationToken(memberDetails);
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return JwtPreAuthenticationToken.class.isAssignableFrom(authentication);
  }
}
