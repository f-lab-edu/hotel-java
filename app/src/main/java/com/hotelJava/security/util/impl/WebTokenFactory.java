package com.hotelJava.security.util.impl;

import org.springframework.security.core.userdetails.UserDetails;

public interface WebTokenFactory {
  String generate(UserDetails userDetails);
}
