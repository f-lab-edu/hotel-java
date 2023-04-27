package com.hotelJava.security;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import com.hotelJava.member.domain.Member;
import com.hotelJava.member.domain.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class MemberDetails extends User {

  public MemberDetails(
      String email, String password, Collection<? extends GrantedAuthority> authorities) {
    super(email, password, authorities);
  }

  public MemberDetails(Member member) {
    super(member.getEmail(), member.getPassword(), parseAuthorities(member.getRole()));
  }

  public String getEmail() {
    return super.getUsername();
  }

  public List<Role> getRole() {
    return super.getAuthorities().stream()
        .map(r -> Role.valueOf(r.getAuthority()))
        .collect(Collectors.toList());
  }

  public static List<SimpleGrantedAuthority> parseAuthorities(Role role) {
    return List.of(new SimpleGrantedAuthority(role.name()));
  }
}
