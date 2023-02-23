package project.toy.api.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import project.toy.api.domain.LostCategory;
import project.toy.api.domain.LostStatus;

@Getter
@Setter
@ToString
public class MemberLostItemVO {

    private String email;

    private String itemName;

    private LostCategory Category;

    private String itemDetailInfo;

    private LostStatus status;
}
