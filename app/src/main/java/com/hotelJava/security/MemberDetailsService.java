package com.hotelJava.security;

import com.hotelJava.member.application.port.out.persistence.FindMemberPort;
import com.hotelJava.member.domain.Member;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberDetailsService implements UserDetailsService {

  private final FindMemberPort findMemberPort;

  @Override
  public UserDetails loadUserByUsername(String email) throws EntityNotFoundException {
    Member member = findMemberPort.findByEmail(email);
    return new MemberDetails(member);
  }
}
