package com.hotelJava.security.provider;

import com.hotelJava.common.error.ErrorCode;
import com.hotelJava.common.error.exception.BadRequestException;
import com.hotelJava.security.MemberDetails;
import com.hotelJava.security.MemberDetailsService;
import com.hotelJava.security.token.EmailPasswordAuthenticationToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberDetailsAuthenticationProvider implements AuthenticationProvider {

  private final MemberDetailsService memberDetailsService;
  private final PasswordEncoder passwordEncoder;

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

    if (memberDetails == null) {
      throw new RuntimeException();
    }
    if (!memberDetails.isEnabled()) {
      throw new BadRequestException(ErrorCode.DISABLED_ACCOUNT);
    }
    if (!memberDetails.isAccountNonLocked()) {
      throw new BadRequestException(ErrorCode.LOCKED_ACCOUNT);
    }
    if (!memberDetails.isCredentialsNonExpired()) {
      throw new BadRequestException(ErrorCode.EXPIRED_PASSWORD);
    }
    if (!isCorrectedPassword(password, memberDetails.getPassword())) {
      throw new BadRequestException(ErrorCode.LOGIN_FAIL);
    }

    return EmailPasswordAuthenticationToken.getPostAuthenticationToken(memberDetails);
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return EmailPasswordAuthenticationToken.class.isAssignableFrom(authentication);
  }

  private boolean isCorrectedPassword(String requestPassWord, String password) {
    return passwordEncoder.matches(requestPassWord, password);
  }
}
