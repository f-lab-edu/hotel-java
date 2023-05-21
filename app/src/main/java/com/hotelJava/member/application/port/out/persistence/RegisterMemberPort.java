package com.hotelJava.member.application.port.out.persistence;

import com.hotelJava.member.domain.Member;

public interface RegisterMemberPort {
  void register(Member member);
}
