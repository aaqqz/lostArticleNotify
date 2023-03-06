package project.toy.api.request;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
public class MemberLostItemEdit {

    @NotBlank(message = "카테고리를 선택해주세요.")
    private String category;

    @NotBlank(message = "분실물 이름을 입력해주세요.")
    private String itemName;

    @NotBlank(message = "분실물 상세내용을 입력해주세요.")
    private String itemDetailInfo;

}
