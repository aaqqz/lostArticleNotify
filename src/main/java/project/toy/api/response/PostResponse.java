package project.toy.api.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostResponse {

    private Long id;
    private String title;
    private String content;
    private String createdBy;
    private String createdAt;

    @Builder
    public PostResponse(Long id, String title, String content, String createdBy, String createdAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
    }


}
