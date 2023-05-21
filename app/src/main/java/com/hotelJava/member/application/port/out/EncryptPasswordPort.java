package com.hotelJava.member.application.port.out;

public interface EncryptPasswordPort {
  String encode(String rawPassword);
}
