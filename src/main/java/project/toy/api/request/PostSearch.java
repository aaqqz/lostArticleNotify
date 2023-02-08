package project.toy.api.request;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostSearch {

    @Builder.Default
    private Integer page = 1;

    @Builder.Default
    private Integer size = 10;

    private String title;

    private String content;

    public long getOffset() {
        return (long) (Math.max(1, page) - 1) * size;
    }
}
