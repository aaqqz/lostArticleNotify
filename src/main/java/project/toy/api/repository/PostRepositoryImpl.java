package project.toy.api.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;
import project.toy.api.domain.Post;
import project.toy.api.request.PostSearch;
import project.toy.api.response.PostResponse;

import java.util.List;

import static org.springframework.util.StringUtils.*;
import static project.toy.api.domain.QMember.member;
import static project.toy.api.domain.QPost.post;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<PostResponse> search(PostSearch postSearch) {
        List<PostResponse> result = queryFactory
                .select(Projections.fields(PostResponse.class,
                        post.id,
                        post.title,
                        post.content,
                        post.createdBy,
                        post.createdAt
                ))
                .from(post)
                .where(
                        titleEq(postSearch.getTitle()), contentEq(postSearch.getContent()))
                .offset(postSearch.getOffset())
                .limit(postSearch.getSize())
                .orderBy(post.createdAt.desc())
                .fetch();

        return null;
//        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private BooleanExpression titleEq(String title) {
        return hasText(title) ? post.title.eq(title) : null;
    }

    private BooleanExpression contentEq(String content) {
        return hasText(content) ? post.content.eq(content) : null;
    }
}
