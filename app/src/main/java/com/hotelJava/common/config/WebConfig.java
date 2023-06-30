package com.hotelJava.common.config;

import com.hotelJava.common.util.IdDecodedConverter;
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

  @Override
  public void addFormatters(FormatterRegistry registry) {
    registry.addConverter(idDecodedConverter);
  }

  @Bean
  public IamportClient iamportClient(
      @Value("${hotel-java.iamport.apiKey}") String apiKey, @Value("${hotel-java.iamport.apiSecret}") String apiSecret) {
    return new IamportClient(apiKey, apiSecret);
  }
}
