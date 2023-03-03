package project.toy.api.scheduler.vo;

import lombok.*;
import project.toy.api.domain.LostCategory;
import project.toy.api.domain.LostStatus;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SendMailVO {

    private String memberLostItemId;

    private String email;

    private LostStatus status;

    private LostCategory category;

    private String itemName;

    private String itemDetailInfo;

    private String takePosition;
}
