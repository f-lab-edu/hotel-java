package com.hotelJava.security.provider;

import com.hotelJava.common.error.ErrorCode;
import com.hotelJava.common.error.exception.BadRequestException;
import com.hotelJava.security.util.impl.MemberPasswordEncoder;
import com.hotelJava.security.MemberDetails;
import com.hotelJava.security.MemberDetailsService;
import com.hotelJava.security.token.EmailPasswordAuthenticationToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberDetailsAuthenticationProvider implements AuthenticationProvider {

  private final MemberDetailsService memberDetailsService;
  private final MemberPasswordEncoder passwordEncoder;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    EmailPasswordAuthenticationToken preAuthToken =
        (EmailPasswordAuthenticationToken) authentication;

    if (authentication == null) {
      throw new RuntimeException();
    }

    String email = preAuthToken.getEmail();
    String password = preAuthToken.getPassword();

    if (password == null) {
      throw new RuntimeException();
    }

    MemberDetails memberDetails = (MemberDetails) memberDetailsService.loadUserByUsername(email);

    if (!memberDetails.isEnabled()) {
      throw new BadRequestException(ErrorCode.DISABLED_ACCOUNT);
    }
    if (!memberDetails.isAccountNonLocked()) {
      throw new BadRequestException(ErrorCode.LOCKED_ACCOUNT);
    }
    if (!memberDetails.isCredentialsNonExpired()) {
      throw new BadRequestException(ErrorCode.EXPIRED_PASSWORD);
    }
    if (!passwordEncoder.matches(password, memberDetails.getPassword())) {
      throw new BadRequestException(ErrorCode.WRONG_PASSWORD);
    }

    return EmailPasswordAuthenticationToken.getPostAuthenticationToken(memberDetails);
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return EmailPasswordAuthenticationToken.class.isAssignableFrom(authentication);
  }
}
