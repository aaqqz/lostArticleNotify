package project.toy.api.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostSearch {

    private String title;

    private String content;

//    @Builder.Default
//    private Integer page = 1;
//
//    @Builder.Default
//    private Integer size = 10;

//    public long getOffset() {
//        return (long) (Math.max(1, page) - 1) * size;
//    }
}
