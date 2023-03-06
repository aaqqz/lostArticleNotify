package project.toy.api.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import project.toy.api.domain.Comment;
import project.toy.api.domain.Member;

import java.util.List;

@Repository
public class CommentRepositoryImpl {

    private JPAQueryFactory query;
//    public List<Comment> getCommentList(){
//        query.select(Projections.fields(CommentResponse.class,
//                ))
//
//
//        return null;
//    }
}
