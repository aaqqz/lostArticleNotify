package project.toy.api.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class CommentCreate {
    // todo 계층형 댓글 작업

    @NotBlank(message = "게시글ID가 없습니다.")
    private Long postId;

    @NotBlank(message = "회원ID가 없습니다.")
    private Long memberId;

    @NotBlank(message = "댓글을 입력해주세요.")
    private String comment;
}
