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
    private LocalDateTime createdAt;
    private String lastModifiedBy;
    private LocalDateTime lastModifiedAt;

    @Builder
    public PostResponse(Long id, String title, String content, String createdBy, LocalDateTime createdAt, String lastModifiedBy, LocalDateTime lastModifiedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.lastModifiedBy = lastModifiedBy;
        this.lastModifiedAt = lastModifiedAt;
    }
}
