package com.hotelJava.payment.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "hotel-java.iamport")
public record HotelJavaIamportConfigurationProperties(String apiKey, String apiSecret) {}
