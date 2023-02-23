package project.toy.api.scheduler.vo;

import lombok.*;
import project.toy.api.domain.LostCategory;
import project.toy.api.domain.LostStatus;

@Data
@NoArgsConstructor
public class SendMailVo {

    private String email;

    private LostStatus status;

    private LostCategory category;

    private String itemName;

    private String itemDetailInfo;

    private String takePosition;
}
