package com.hotelJava.security.token;

import com.hotelJava.security.MemberDetails;
import com.hotelJava.security.util.specification.JwtPayload;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class LoginPostAuthenticationToken extends UsernamePasswordAuthenticationToken {
  public LoginPostAuthenticationToken(MemberDetails memberDetails) {
    super(memberDetails.getEmail(), memberDetails.getPassword(), memberDetails.getAuthorities());
  }

  public JwtPayload getJwtPayload() {
    return JwtPayload.builder().subject(getEmail()).roles(getRoles()).build();
  }

  public String getEmail() {
    return (String) super.getPrincipal();
  }

  public String getPassword() {
    return (String) super.getCredentials();
  }

  public List<String> getRoles() {
    return super.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.toList());
  }
}
