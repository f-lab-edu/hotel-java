package com.hotelJava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class HotelJavaApplication {
  public static void main(String[] args) {
    SpringApplication.run(HotelJavaApplication.class, args);
  }
}
