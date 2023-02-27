package project.toy.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import project.toy.api.request.MemberLostItemCreate;
import project.toy.api.response.CommonResponse;
import project.toy.api.service.MemberLostItemService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class MemberLostItemController {
    // todo 회원 등록 분실물

    private final MemberLostItemService memberLostItemService;

    @PostMapping("/memberLostItem")
    public ResponseEntity<CommonResponse> write(@RequestBody @Valid MemberLostItemCreate memberLostItemCreate){
        memberLostItemService.save(memberLostItemCreate);

        return ResponseEntity.ok().body(CommonResponse.defaultCommonResponse());
    }
}
