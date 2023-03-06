package project.toy.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.toy.api.domain.MemberLostItem;
import project.toy.api.request.MemberLostItemCreate;
import project.toy.api.request.MemberLostItemEdit;
import project.toy.api.response.CommonResponse;
import project.toy.api.service.MemberLostItemService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class MemberLostItemController {

    private final MemberLostItemService memberLostItemService;

    @PostMapping("/memberLostItem")
    public ResponseEntity<CommonResponse> save(@RequestBody @Valid MemberLostItemCreate create){
        memberLostItemService.save(create);

        return ResponseEntity.ok().body(CommonResponse.defaultCommonResponse());
    }

    @PatchMapping("/memberLostItem/{id}")
    public CommonResponse edit(@PathVariable Long id, @RequestBody @Valid MemberLostItemEdit edit) {
        MemberLostItem editItem = memberLostItemService.edit(id, edit);

        return CommonResponse.defaultCommonResponse(editItem);
    }
}
