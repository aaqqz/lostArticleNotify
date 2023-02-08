package project.toy.api.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
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
    public Page<PostResponse> search(PostSearch postSearch, Pageable pageable) {
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
                        titleLike(postSearch.getTitle()),
                        contentLike(postSearch.getContent()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(post.id.desc())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(post.count())
                .from(post)
                .where(
                        titleLike(postSearch.getTitle()),
                        contentLike(postSearch.getContent())
                );

        return PageableExecutionUtils.getPage(result, pageable, countQuery::fetchOne);
    }

    private BooleanExpression titleLike(String title) {
        return hasText(title) ? post.title.contains(title) : null;
    }

    private BooleanExpression contentLike(String content) {
        return hasText(content) ? post.content.contains(content) : null;
    }
}
