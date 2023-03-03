package project.toy.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.toy.api.domain.Comment;
import project.toy.api.domain.Member;
import project.toy.api.domain.Post;
import project.toy.api.exception.ParentCommentNotFound;
import project.toy.api.exception.PostNotFound;
import project.toy.api.repository.CommentRepository;
import project.toy.api.repository.MemberRepository;
import project.toy.api.repository.PostRepository;
import project.toy.api.request.CommentCreate;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public Comment write(CommentCreate commentCreate) {

        Post findPost = postRepository.findById(commentCreate.getPostId())
                .orElseThrow(() -> new PostNotFound());

        Member findMember = memberRepository.findById(commentCreate.getMemberId())
                .orElseThrow();

        Comment parentComment = commentRepository.findById(commentCreate.getParentId()).orElseThrow(() -> new ParentCommentNotFound());


        Comment comment = Comment.builder()
                .comment(commentCreate.getComment())
                .member(findMember)
                .depthNumber(commentCreate.getDepthNumber())
                .parentComment(parentComment)
                .post(findPost).build();

        commentRepository.save(comment);

        return comment;
    }
}
