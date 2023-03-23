package com.hotelJava.member.repository;

import com.hotelJava.member.domain.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class MemberRepository {

  @PersistenceContext private final EntityManager em;

  public Member findOne(Member member) {
    return em.find(Member.class, member.getId());
  }

  public Member findByEmail(String email) {
    String query = "SELECT m FROM Member m WHERE m.email = :email";
    List<Member> members = em.createQuery(query, Member.class).setParameter("email", email).getResultList();
    if (members.isEmpty()) {
      return null;
    }
    return members.get(0);
  }

  @Transactional
  public void save(Member member) {
    em.persist(member);
  }
}
