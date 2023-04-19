package com.hotelJava.security.config;

public class JwtConfig {
  public static final String ISSUER = "hotel-java";
  public static final String SECRET = "hotel-java-secret";
  public static final long EXPIRE_TIME = 60000; // 10분
}
