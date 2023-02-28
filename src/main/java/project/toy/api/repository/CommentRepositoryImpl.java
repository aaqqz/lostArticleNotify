package project.toy.api.repository;

import org.springframework.stereotype.Repository;
import project.toy.api.domain.Comment;
import project.toy.api.domain.Member;

@Repository
public class CommentRepositoryImpl {

    public void save(){
        Comment comment = Comment.builder()
                .comment("testtest")
                .depthNumber(0)
                .build();
    }
}
