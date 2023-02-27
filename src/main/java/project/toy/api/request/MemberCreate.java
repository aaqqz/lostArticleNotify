package project.toy.api.request;

import lombok.Builder;
import lombok.Getter;

import javax.validation.Valid;
import javax.validation.constraints.*;

@Getter
public class MemberCreate {

    @NotBlank(message = "이름을 입력해주세요.")
    @DecimalMax(value = "20", message = "20글자를 넘을 수 없습니다.")
    @DecimalMin(value = "5", message = "5글자보다 커야 합니다.")
    private String name;

    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "이메일 형식에 맞지 않습니다.")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
            message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.")
    private String password;

    @Builder
    public MemberCreate(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public void validate() {
        if (!name.contains("관리자")){
            throw new RuntimeException("다른 이름을 입력해주세요.");
        }
    }
}
