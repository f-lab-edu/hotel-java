package com.hotelJava.member.domain;

public enum Role {
  USER("USER"),
  ADMIN("SELLER");

  private final String role;

  Role(String role) {
    this.role = role;
  }
}
