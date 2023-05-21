package com.hotelJava.member.application.port.out;

public interface MatchPasswordPort {
  boolean matches(String rawPassword, String encryptedPassword);
}
