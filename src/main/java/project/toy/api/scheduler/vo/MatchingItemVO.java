package project.toy.api.scheduler.vo;

import lombok.Getter;
import project.toy.api.domain.LostCategory;
import project.toy.api.domain.LostStatus;

@Getter
public class MatchingItemVO {

    private Long memberLostItemId;

    private LostStatus status;          // 분실물 상태

    private LostCategory category;      // 분실물 종류

    private String itemName;            // 분실물명

    private String itemDetailInfo;      // 분실물 상세내용

    private String takePosition;        // 수령위치(회사)
}
