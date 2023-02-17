package project.toy.api.config.data;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class MemberSession {

    private Long memberId;

    private String memberName;

    public MemberSession(Long memberId, String memberName) {
        this.memberId = memberId;
        this.memberName = memberName;
    }
}
