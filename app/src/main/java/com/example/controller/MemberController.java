package com.example.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    @GetMapping
    public String getAllMember() {

        // memberService 에게 모든 가입회원 정보를 가져오라고 메시지 전달
        // allMember = memberService.findAll();

        // allMember 를 반환
        return "{{'id':'1L', 'name':'userA', 'password': 'userA12345!', 'phoneNumber':'010-1111-2222', 'email':'userA@gmail.com'}," +
                "{'id':'2L', 'name':'userB', 'password': 'userB223P5!', 'phoneNumber':'010-3333-4444', 'email': 'userB@gmail.com'}," +
                "{'id':'3L', 'name': 'userC', 'password': 'userC333!', 'phoneNumber':'010-4444-5555', 'email': 'userC@gmail.com'}," +
                "{'id':'4L', 'name': 'userD', 'password': 'userD444@', 'phoneNumber': '010-5555-6666', 'email': 'userD@gmail.com'}}";
    }

    @GetMapping("/{id}")
    public String getMember(@PathVariable String id) {

        // memberService 에게 db.id == Parameter.id 를 만족하는 회원 정보를 가져오라고 메시지 전달
        // member = memberService.find(id);

        // member 를 반환
        return "{'id':'1L'," +
                "'name':'userA'," +
                "'password': 'userA12345!'," +
                "'phoneNumber':'010-1111-2222'," +
                "'email':'userA@gmail.com'}";
    }

    @PostMapping
    public String signUp(String memberSignUpForm) {
        // String memberSignUpForm: 회원가입 화면에서 Form 방식으로 서버에 보내온 정보

        // memberService 에게 회원가입 메시지 전달
        // memberService.signUp(memberSignUpForm)

        // 회원가입 결과를 반환
        return "200 ok";
    }

    @PutMapping
    public String updateUser(@SessionAttribute String memberId, String memberUpdateForm) {
        // String memberId: Session 저장소에 저장되어있는 회원 id
        // String memberUpdateForm: 회원정보수정 화면에서 Form 방식으로 서버에 보내온 정보
        // memberService 에게 회원정보를 수정하라고 메시지 전달
        // memberService.update(memberId, memberUpdateForm);

        //회원정보수정 결과를 반환
        return "200 ok";
    }

    @DeleteMapping
    public String deleteUser(@SessionAttribute String memberId) {
        // String memberId: Session 저장소에 저장되어있는 회원 id
        // memberService 에게 회원정보를 삭제하라고 메시지 전달
        // memberService.delete(memberId);

        //회원탈퇴 결과를 반환
        return "200 ok";
    }
}
