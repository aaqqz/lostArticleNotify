package project.toy.api.response;

import lombok.Builder;
import lombok.Getter;
import project.toy.api.domain.Post;
import project.toy.api.repository.PostRepository;

import java.time.LocalDateTime;

@Getter
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
