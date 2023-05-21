package com.hotelJava.member.application.port.out.persistence;

import com.hotelJava.member.domain.Member;

public interface FindMemberPort {
  Member findByEmail(String email);
}
