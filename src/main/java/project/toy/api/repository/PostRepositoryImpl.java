package project.toy.api.repository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import project.toy.api.domain.Post;
import project.toy.api.domain.QPost;
import project.toy.api.request.PostSearch;

import java.util.List;

import static project.toy.api.domain.QPost.*;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Post> getList(PostSearch postSearch) {
        return queryFactory
                .selectFrom(post)
                .fetch();
    }
}
