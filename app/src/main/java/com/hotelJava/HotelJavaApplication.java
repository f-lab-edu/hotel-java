package com.hotelJava;

import com.hotelJava.payment.config.PaymentConfigurationProperties;
import com.hotelJava.security.config.JwtConfigurationProperties;
import com.siot.IamportRestClient.IamportClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableJpaAuditing
@EnableConfigurationProperties({
  JwtConfigurationProperties.class,
  PaymentConfigurationProperties.class
})
public class HotelJavaApplication {
  public static void main(String[] args) {
    SpringApplication.run(HotelJavaApplication.class, args);
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public IamportClient iamportClient(PaymentConfigurationProperties properties) {
    return new IamportClient(properties.apiKey(), properties.apiSecret());
  }
}
