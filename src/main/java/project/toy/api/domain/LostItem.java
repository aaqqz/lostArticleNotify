package project.toy.api.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class LostItem extends BaseTimeEntity{

    @Id
    @Column(name = "LOST_ITEM_ID")
    private String id;                  // 분실물 SEQ

    @Enumerated(EnumType.STRING)
    private LostStatus status;          // 분실물 상태

    @Enumerated(EnumType.STRING)
    private LostCategory category;      // 분실물 종류

    private String itemName;            // 분실물명

    private String itemDetailInfo;      // 분실물 상세내용

    private String takePlace;           // 분실장소

    private String takePosition;        // 수령위치(회사)

    private String regDate;             // 등록일 (홈페이지 등록일)

    private String getDate;             // 수령일 (습득일)

    @Builder
    public LostItem(String id, LostStatus status, LostCategory category, String itemName, String itemDetailInfo, String takePlace, String takePosition, String regDate, String getDate) {
        this.id = id;
        this.status = status;
        this.category = category;
        this.itemName = itemName;
        this.itemDetailInfo = itemDetailInfo;
        this.takePlace = takePlace;
        this.takePosition = takePosition;
        this.regDate = regDate;
        this.getDate = getDate;
    }
}
