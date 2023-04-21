package com.hotelJava.security;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.javafaker.Faker;
import com.hotelJava.member.domain.Role;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

class MemberDetailsTest {

  private final Faker faker = Faker.instance();

  @Test
  @DisplayName("Role 이 주어지면 List<SimpleGrantedAuthority> 가 반환된다")
  void parseAuthorities_list() {
    // given
    Role role = faker.options().option(Role.class);

    // when
    List<SimpleGrantedAuthority> authorities = MemberDetails.parseAuthorities(role);

    // then
    assertThat(authorities).isEqualTo(List.of(new SimpleGrantedAuthority(role.name())));
  }
}
