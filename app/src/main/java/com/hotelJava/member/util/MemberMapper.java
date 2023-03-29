package com.hotelJava.member.util;

import com.hotelJava.member.domain.Member;
import com.hotelJava.member.dto.SignUpRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * MapStruct가 자동으로 MemberMapper의 구현체를 생성, 상수 MAPPER에 주입
 * MAPPER를 통해 객체타입 간의 매핑 기능을 이용할 수 있다.
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MemberMapper {
  MemberMapper MAPPER = Mappers.getMapper(MemberMapper.class);

  Member toEntity(SignUpRequestDto signUpRequestDto);

  SignUpRequestDto toSignUpRequestDto(Member member);
}
