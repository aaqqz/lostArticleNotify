package project.toy.api.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.toy.api.exception.InvalidRequest;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class PostCreate {

    @NotBlank(message = "제목을 입력해주세요.")
    private String title;

    @NotBlank(message = "내용을 입력해주세요.")
    private String content;

    @Builder
    public PostCreate(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void validate() {
        if (title.contains("금지어")) {
            throw new InvalidRequest("title", "제목에 '금지어'를 포함할 수 없습니다.");
        }
    }
}