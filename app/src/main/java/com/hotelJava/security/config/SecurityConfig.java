package com.hotelJava.security.config;

import com.hotelJava.security.filter.ExceptionHandlerFilter;
import com.hotelJava.security.filter.LoginFilter;
import com.hotelJava.security.provider.MemberDetailsAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig {

  private static final String API_URL = "/api/*";
  private static final String LOGIN_URL = "/login";
  private static final String SIGNUP_URL = "/api/members";

  @Autowired private MemberDetailsAuthenticationProvider memberDetailsAuthenticationProvider;
  @Autowired private AuthenticationSuccessHandler authenticationSuccessHandler;
  @Autowired private ExceptionHandlerFilter exceptionHandlerFilter;
  @Autowired private AccessDeniedHandler accessDeniedHandler;
  @Autowired private AuthenticationEntryPoint authenticationEntryPoint;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
    http.httpBasic().disable(); // authorization 헤더를 JWT 토큰을 담는 용도로 사용하기 위해 꺼둔다.

    // 해당 기능을 켜두면 CSRF 공격을 방어하기 위한 토큰을 세션 저장소에 저장
    // CSRF 토큰을 JWT 토큰으로 대체할 수 있으므로 해당 옵션 off
    http.csrf().disable();

    // 인증에 성공하면 SecurityContext 객체를 세션 저장소에 저장
    // Jwt 토큰을 기반으로 인증할 것이므로 해당 옵션 off
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    // == URL 인가 설정 ==//
    http.authorizeHttpRequests()
        .requestMatchers(HttpMethod.POST, SIGNUP_URL, LOGIN_URL)
        .permitAll()
        .requestMatchers(API_URL)
        .authenticated();

    // == 필터 및 프로바이더 등록 ==//
    http.addFilterBefore(
            loginFilter(authenticationManager), UsernamePasswordAuthenticationFilter.class)
        .addFilterBefore(exceptionHandlerFilter, LoginFilter.class)
        .authenticationProvider(memberDetailsAuthenticationProvider);

    // == 예외처리 ==//
    http.exceptionHandling()
        .accessDeniedHandler(accessDeniedHandler)
        .authenticationEntryPoint(authenticationEntryPoint);

    return http.build();
  }

  @Bean
  AuthenticationManager authenticationManager(
      AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public LoginFilter loginFilter(AuthenticationManager authenticationManager) {
    LoginFilter filter = new LoginFilter(LOGIN_URL);
    filter.setAuthenticationManager(authenticationManager);
    filter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
    return filter;
  }
}
