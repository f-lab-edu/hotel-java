package com.hotelJava.security.token;

import com.hotelJava.security.MemberDetails;
import com.hotelJava.security.dto.LoginDto;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import java.util.Collection;

public class EmailPasswordAuthenticationToken extends UsernamePasswordAuthenticationToken {

  protected EmailPasswordAuthenticationToken(LoginDto dto) {
    super(dto.email(), dto.password());
  }

  protected EmailPasswordAuthenticationToken(
      String email, String password, Collection<? extends GrantedAuthority> authorities) {
    super(email, password, authorities);
  }

  public static EmailPasswordAuthenticationToken getPreAuthenticationToken(LoginDto dto) {
    return new EmailPasswordAuthenticationToken(dto);
  }

  public static EmailPasswordAuthenticationToken getPostAuthenticationToken(
      MemberDetails memberDetails) {
    return new EmailPasswordAuthenticationToken(
        memberDetails.getEmail(), memberDetails.getPassword(), memberDetails.getAuthorities());
  }

  public String getEmail() {
    return (String) super.getPrincipal();
  }

  public String getPassword() {
    return (String) super.getCredentials();
  }
}
