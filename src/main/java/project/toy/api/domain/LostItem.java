package project.toy.api.domain;

import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
public class LostItem {

    @Id
    private Long id;                    // 분실물 SEQ

    private LostStatus status;          // 분실물 상태

    private LostCategory category;      // 분실물 종류

    private String itemName;            // 분실물명

    private String itemDetailInfo;      // 분실물 상세내용

    private String takePlace;           // 분실장소

    private String takePosition;        // 수령위치(회사)

    private LocalDate regDate;          // 등록일 (홈페이지 등록일)

    private LocalDate getDate;          // 수령일 (습득일)

    @Builder
    public LostItem(Long id, LostStatus status, LostCategory category, String itemName, String itemDetailInfo, String takePlace, String takePosition, LocalDate regDate, LocalDate getDate) {
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
