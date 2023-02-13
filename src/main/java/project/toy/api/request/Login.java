package project.toy.api.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class Login {

    @NotBlank(message = "아이디를 입력하세요.")
    private String id;

    @NotBlank(message = "비밀번호를 입력하세요.")
    private String password;
}
