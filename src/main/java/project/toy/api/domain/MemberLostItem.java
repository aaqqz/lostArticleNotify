package project.toy.api.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class MemberLostItem extends BaseEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LOSTITEM_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @Enumerated(EnumType.STRING)
    private LostCategory category;       // 분실물 종류

    private String itemName;             // 분실물명

    private String itemDetailInfo;      // 분실물 상세내용


    @Builder
    public MemberLostItem(Member member, LostCategory category, String itemName, String itemDetailInfo){
        this.member = member;
        if(!member.getMemberLostItems().contains(this)){
            member.getMemberLostItems().add(this);
        }
        this.category = category;
        this.itemName = itemName;
        this.itemDetailInfo = itemDetailInfo;
    }

    public LostCategory getLostCategory(String category) {
        switch (category) {
            case "가방":
                return LostCategory.BAG;
            case "배낭":
                return LostCategory.BACKPACK;
            case "서류봉투":
                return LostCategory.ENVELOPE;
            case "쇼핑백":
                return LostCategory.SHOPBAG;
            case "옷":
                return LostCategory.CLOTHES;
            case "장난감":
                return LostCategory.TOY;
            case "지갑":
                return LostCategory.WALLET;
            case "책":
                return LostCategory.BOOK;
            case "파일":
                return LostCategory.FILE;
            case "핸드폰":
                return LostCategory.MOBILE;
            default:
                return LostCategory.ETC;
        }
    }
}
