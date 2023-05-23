package com.hotelJava.security;

import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
public record JwtConfigurationProperties(String issuer, String secret, Duration validityTime) {}
