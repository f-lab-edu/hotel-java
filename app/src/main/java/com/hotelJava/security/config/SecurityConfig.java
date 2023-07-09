package com.hotelJava.security.config;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.*;

import com.hotelJava.security.filter.ExceptionHandlerFilter;
import com.hotelJava.security.filter.FilterSkipMatcher;
import com.hotelJava.security.filter.JwtAuthenticationFilter;
import com.hotelJava.security.filter.LoginAuthenticationFilter;
import com.hotelJava.security.handler.LoginAuthenticationSuccessHandler;
import com.hotelJava.security.provider.JwtAuthenticationProvider;
import com.hotelJava.security.provider.MemberDetailsAuthenticationProvider;
import com.hotelJava.security.util.impl.HeaderTokenExtractor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig {

  private static final String API_URL = "/api/**";
  private static final String LOGIN_URL = "/login";
  private static final String SIGNUP_URL = "/api/members";

  private final MemberDetailsAuthenticationProvider loginDetailsAuthenticationProvider;
  private final LoginAuthenticationSuccessHandler loginAuthenticationSuccessHandler;
  private final JwtAuthenticationProvider jwtAuthenticationProvider;
  private final AccessDeniedHandler accessDeniedHandler;
  private final AuthenticationEntryPoint authenticationEntryPoint;
  private final ExceptionHandlerFilter exceptionHandlerFilter;
  private final HeaderTokenExtractor extractor;

  @Value("${spring.security.enabled}")
  private boolean securityEnabled;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager manager)
      throws Exception {

    // 해당 기능을 켜두면 Authorization 헤더를 기본적으로 사용
    // authorization 헤더를 JWT 토큰을 담는 용도로 사용하기 위해 해당 옵션 off
    http.httpBasic().disable();

    // 해당 기능을 켜두면 CSRF 공격을 방어하기 위한 토큰을 세션 저장소에 저장
    // CSRF 토큰을 JWT 토큰으로 대체할 수 있으므로 해당 옵션 off
    http.csrf().disable();

    // 인증에 성공하면 SecurityContext 객체를 세션 저장소에 저장
    // Jwt 토큰을 기반으로 인증할 것이므로 해당 옵션 off
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);


    if (securityEnabled) {
      return enableSecurity(http, manager);
    }
    return disableSecurity(http);
  }

  private SecurityFilterChain disableSecurity(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests().anyRequest().anonymous();
    return http.build();
  }

  private SecurityFilterChain enableSecurity(HttpSecurity http, AuthenticationManager manager)
      throws Exception {
    // == URL 인가 설정 == //
    http.authorizeHttpRequests()
            // TODO: 예약, 결제에 대해서 테스트로 URL 추가
        .requestMatchers(HttpMethod.POST, SIGNUP_URL, LOGIN_URL, "/api/reservations/**", "/api/payments/**")
        .anonymous()
        .requestMatchers(HttpMethod.GET, "/reservations")
        .anonymous()
        .anyRequest()
        .authenticated();

    // == 필터 등록 == //
    http.authenticationManager(manager)
        .addFilterBefore(loginFilter(manager), UsernamePasswordAuthenticationFilter.class)
        .addFilterBefore(jwtFilter(manager), UsernamePasswordAuthenticationFilter.class)
        .addFilterBefore(exceptionHandlerFilter, LoginAuthenticationFilter.class)
        .addFilterBefore(encodingFilter(), CsrfFilter.class);

    // == 예외처리 == //
    http.exceptionHandling()
        .accessDeniedHandler(accessDeniedHandler)
        .authenticationEntryPoint(authenticationEntryPoint);

    return http.build();
  }

  @Bean
  AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
    AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
    return builder
        .authenticationProvider(loginDetailsAuthenticationProvider)
        .authenticationProvider(jwtAuthenticationProvider)
        .build();
  }

  public LoginAuthenticationFilter loginFilter(AuthenticationManager authenticationManager) {
    LoginAuthenticationFilter filter = new LoginAuthenticationFilter(LOGIN_URL);
    filter.setAuthenticationManager(authenticationManager);
    filter.setAuthenticationSuccessHandler(loginAuthenticationSuccessHandler);
    return filter;
  }

  public JwtAuthenticationFilter jwtFilter(AuthenticationManager authenticationManager) {
    FilterSkipMatcher matcher =
        new FilterSkipMatcher(
            API_URL, antMatcher(LOGIN_URL), antMatcher(HttpMethod.POST, SIGNUP_URL));
    JwtAuthenticationFilter filter = new JwtAuthenticationFilter(matcher, extractor);
    filter.setAuthenticationManager(authenticationManager);
    return filter;
  }

  public CharacterEncodingFilter encodingFilter() {
    CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
    encodingFilter.setEncoding("UTF-8");
    encodingFilter.setForceEncoding(true);
    return encodingFilter;
  }
}
