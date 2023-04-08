package com.hotelJava.security.provider;

import com.hotelJava.common.error.ErrorCode;
import com.hotelJava.common.error.exception.BadRequestException;
import com.hotelJava.common.error.exception.InternalServerException;
import com.hotelJava.security.MemberDetails;
import com.hotelJava.security.MemberDetailsService;
import com.hotelJava.security.token.PostAuthenticationToken;
import com.hotelJava.security.token.PreAuthenticationToken;
import com.hotelJava.security.util.impl.MemberPasswordEncoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MemberDetailsAuthenticationProvider implements AuthenticationProvider {

  private final MemberDetailsService memberDetailsService;
  private final MemberPasswordEncoder passwordEncoder;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    if (authentication == null) {
      log.info("authentication null");
      throw new InternalServerException(ErrorCode.BAD_CREDENTIAL);
    }

    PreAuthenticationToken preAuthToken = (PreAuthenticationToken) authentication;

    String email = preAuthToken.getEmail();
    String password = preAuthToken.getPassword();

    if (password == null) {
      log.info("password null");
      throw new BadRequestException(ErrorCode.EXPIRED_PASSWORD);
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

    return PostAuthenticationToken.generate(memberDetails);
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return PreAuthenticationToken.class.isAssignableFrom(authentication);
  }
}
