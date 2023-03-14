package com.example.controller;

import com.example.dto.DeleteMemberResponseDto;
import com.example.dto.MemberDto;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

@RestController
@RequestMapping("/api/members")
public class MemberController {

  @GetMapping("/{id}")
  public MemberDto findById(@PathVariable String id) {

    // memberService 에게 db.id == Parameter.id 를 만족하는 회원 정보를 가져오라고 메시지 전달
    // member = memberService.find(id);

    // member 를 반환
    return MemberDto.builder()
        .email("userA@gmail.com")
        .name("userA")
        .password("userA12345!")
        .phoneNumber("010-1111-2222")
        .build();
  }

  @PostMapping
  public void signUp(@RequestBody MemberDto memberInfo) {
    // String memberSignUpForm: 회원가입 화면에서 Form 방식으로 서버에 보내온 정보

    // memberService 에게 회원가입 메시지 전달
    // memberService.signUp(memberSignUpForm)
  }

  @PutMapping
  public void update(@SessionAttribute String memberId, String memberUpdateForm) {
    // String memberId: Session 저장소에 저장되어있는 회원 id
    // String memberUpdateForm: 회원정보수정 화면에서 Form 방식으로 서버에 보내온 정보
    // memberService 에게 회원정보를 수정하라고 메시지 전달
    // memberService.update(memberId, memberUpdateForm);
  }

  @DeleteMapping
  public DeleteMemberResponseDto delete(@SessionAttribute String memberId) {
    // String memberId: Session 저장소에 저장되어있는 회원 id
    // memberService 에게 회원정보를 삭제하라고 메시지 전달
    // memberService.delete(memberId);

    // 회원탈퇴 결과를 반환
    return DeleteMemberResponseDto.builder()
        .email("deletedUser@gmail.com")
        .name("deletedUserName")
        .build();
  }
}
