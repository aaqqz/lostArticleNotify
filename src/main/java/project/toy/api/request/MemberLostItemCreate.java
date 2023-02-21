package project.toy.api.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class MemberLostItemCreate {

    //pk id
    @NotBlank(message = "멤버 키가 없습니다.")
    private Long memberId;

    @NotBlank(message = "카테고리 없습니다.")
    private String category;

    @NotBlank(message = "분실물 이름이 없습니다.")
    private String itemName;       // 분실물명

    @NotBlank(message = "분실물 상세 내용이 없습니다.")
    private String itemDetailInfo;   // 분실물 상세내용

}
