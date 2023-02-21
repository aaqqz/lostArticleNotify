package project.toy.api.scheduler.vo;

import lombok.*;
import project.toy.api.domain.LostCategory;
import project.toy.api.domain.LostStatus;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class SendMailVO {

    private String email;

    private LostCategory category;

    private String itemName;

    private String itemDetailInfo;

    private String takePosition;

    private LostStatus status;


}
