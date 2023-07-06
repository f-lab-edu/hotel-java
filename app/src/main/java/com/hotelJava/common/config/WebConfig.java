package com.hotelJava.common.config;

import com.hotelJava.common.util.IdDecodedConverter;
import com.hotelJava.payment.config.HotelJavaIamportConfigurationProperties;
import com.siot.IamportRestClient.IamportClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

  private final IdDecodedConverter idDecodedConverter;
  private final HotelJavaIamportConfigurationProperties properties;

  @Override
  public void addFormatters(FormatterRegistry registry) {
    registry.addConverter(idDecodedConverter);
  }

  @Bean
  public IamportClient iamportClient() {
    return new IamportClient(properties.apiKey(), properties.apiSecret());
  }
}
