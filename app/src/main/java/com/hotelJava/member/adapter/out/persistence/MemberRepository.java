package com.hotelJava.member.adapter.out.persistence;

import com.hotelJava.member.domain.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface MemberRepository extends JpaRepository<Member, Long> {
  Boolean existsByEmail(String email);

  Optional<Member> findByEmail(String email);
}
