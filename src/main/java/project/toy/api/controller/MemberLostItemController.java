package project.toy.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import project.toy.api.request.MemberLostItemCreate;
import project.toy.api.response.CommonResponse;
import project.toy.api.service.MemberLostItemService;

import javax.validation.Valid;

@RestController
public class MemberLostItemController {

    private MemberLostItemService memberLostItemService;

    @PostMapping("/lostItem/{lostItemId}")
    public ResponseEntity<CommonResponse> write(@PathVariable int lostItemId, @RequestBody @Valid MemberLostItemCreate memberLostItemCreate){
        memberLostItemService.save(memberLostItemCreate);

        return ResponseEntity.ok().body(CommonResponse.defaultCommonResponse());
    }
}
