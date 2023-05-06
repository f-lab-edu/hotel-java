package com.hotelJava.common.config;

import com.hotelJava.common.util.IdDecodedConverter;
import lombok.RequiredArgsConstructor;
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
}


