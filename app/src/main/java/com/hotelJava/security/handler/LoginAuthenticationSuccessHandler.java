package com.hotelJava.security.handler;

import com.hotelJava.security.token.LoginPostAuthenticationToken;
import com.hotelJava.security.util.impl.JwtTokenFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoginAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

  private final JwtTokenFactory jwtTokenFactory;

  @Override
  public void onAuthenticationSuccess(
      HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
    LoginPostAuthenticationToken postAuthToken = (LoginPostAuthenticationToken) authentication;

    String token =
        jwtTokenFactory.generate(postAuthToken.getJwtPayload(), System.currentTimeMillis());

    response.setHeader("Authorization", token);
  }
}
