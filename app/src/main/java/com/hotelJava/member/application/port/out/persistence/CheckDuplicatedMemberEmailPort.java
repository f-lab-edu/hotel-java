package com.hotelJava.member.application.port.out.persistence;

public interface CheckDuplicatedMemberEmailPort {
  boolean isDuplicated(String email);
}
