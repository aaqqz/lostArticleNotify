package project.toy.api.response;

import lombok.*;
import project.toy.api.domain.Comment;

import java.util.List;

@Getter
@ToString
@NoArgsConstructor
public class CommentResponse {

    private Long id;
    private String comment;
    private String createdBy;
    private String createdAt;

    private Long parentId;

    private List<CommentResponse> childComments;


    @Builder
    public CommentResponse(Long id, String comment, String createdBy, String createdAt, Long parentId, List<CommentResponse> childComments, List<Comment> comments){
        this.id = id;
        this.parentId = parentId;
        this.childComments = childComments;
        this.comment = comment;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
    }
}
