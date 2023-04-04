package com.hotelJava.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  private static final String API_URL = "/api/*";
  private static final String LOGIN_URL = "/login";
  private static final String SIGNUP_URL = "/api/members";

  @Autowired private MemberDetailsAuthenticationProvider memberDetailsAuthenticationProvider;
  @Autowired private ObjectMapper objectMapper;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
    http.sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authorizeHttpRequests()
        .requestMatchers(HttpMethod.POST, SIGNUP_URL)
        .permitAll()
        .requestMatchers(API_URL)
        .authenticated()
        .and()
        .addFilterBefore(
            loginFilter(authenticationManager), UsernamePasswordAuthenticationFilter.class)
        .authenticationProvider(memberDetailsAuthenticationProvider);
    return http.build();
  }

  @Bean
  AuthenticationManager authenticationManager(
      AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public LoginFilter loginFilter(AuthenticationManager authenticationManager) {
    LoginFilter filter = new LoginFilter(LOGIN_URL, objectMapper);
    filter.setAuthenticationManager(authenticationManager);
    return filter;
  }
}
