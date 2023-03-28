package com.hotelJava.member.repository;

import com.hotelJava.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
  Boolean existsByEmail(String email);
}
