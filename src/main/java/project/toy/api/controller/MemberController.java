package project.toy.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import project.toy.api.domain.Member;
import project.toy.api.request.MemberCreate;
import project.toy.api.service.MemberService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class MemberController {
    // todo 회원가입

    private final MemberService memberService;

    @PostMapping("/member")
    public void join(@RequestBody @Valid MemberCreate memberCreate) {
        memberCreate.validate();
        Member joinMember = memberService.join(memberCreate);
    }
}
