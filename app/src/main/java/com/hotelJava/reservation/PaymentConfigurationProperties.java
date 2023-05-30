package com.hotelJava.reservation;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "payment-gateway")
public record PaymentConfigurationProperties(String apiKey, String apiSecret) {}
