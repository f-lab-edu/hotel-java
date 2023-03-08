package com.example.controller;

import com.example.dto.DeleteMemberResponse;
import com.example.dto.MemberResponseDto;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    @GetMapping
    public List<MemberResponseDto> findAll() {

        // memberService 에게 모든 가입회원 정보를 가져오라고 메시지 전달
        // allMember = memberService.findAll();

        // allMember 를 반환
        MemberResponseDto memberA = MemberResponseDto.builder()
                .id(1L)
                .email("userA@gmail.com")
                .name("userA")
                .password("userA12345!")
                .phoneNumber("010-1111-2222").build();

        MemberResponseDto memberB = MemberResponseDto.builder()
                .id(2L)
                .email("userB@gmail.com")
                .name("userB")
                .password("userB12345!")
                .phoneNumber("010-2222-3333").build();

        MemberResponseDto memberC = MemberResponseDto.builder()
                .id(3L)
                .email("userC@gmail.com")
                .name("userC")
                .password("userC12345!")
                .phoneNumber("010-3333-4444").build();

        return List.of(memberA, memberB, memberC);
    }

    @GetMapping("/{id}")
    public MemberResponseDto findById(@PathVariable String id) {

        // memberService 에게 db.id == Parameter.id 를 만족하는 회원 정보를 가져오라고 메시지 전달
        // member = memberService.find(id);

        // member 를 반환
        return MemberResponseDto.builder()
                .id(1L)
                .email("userA@gmail.com")
                .password("userA12345!")
                .phoneNumber("010-1111-2222").build();
    }

    @PostMapping
    public void signUp(String memberSignUpForm) {
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
    public DeleteMemberResponse delete(@SessionAttribute String memberId) {
        // String memberId: Session 저장소에 저장되어있는 회원 id
        // memberService 에게 회원정보를 삭제하라고 메시지 전달
        // memberService.delete(memberId);

        //회원탈퇴 결과를 반환
        return DeleteMemberResponse.builder()
                .email("deletedUser@gmail.com")
                .name("deletedUserName").build();
    }
}
