package com.hotelJava.security.token;

import com.hotelJava.security.MemberDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class JwtPostAuthenticationToken extends UsernamePasswordAuthenticationToken {
  public JwtPostAuthenticationToken(MemberDetails memberDetails) {
    super(memberDetails, memberDetails.getPassword(), memberDetails.getAuthorities());
  }
}
