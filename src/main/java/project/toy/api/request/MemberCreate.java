package project.toy.api.request;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class MemberCreate {

    @NotBlank(message = "이름을 입력해주세요.")
    private String name;

    @Builder
    public MemberCreate(String name) {
        this.name = name;
    }

    public void validate() {
        if (!name.contains("관리자")){
            throw new RuntimeException("다른 이름을 입력해주세요.");
        }
    }
}
