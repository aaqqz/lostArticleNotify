package project.toy.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import project.toy.api.domain.Comment;
import project.toy.api.domain.Member;
import project.toy.api.domain.Post;
import project.toy.api.exception.ParentCommentNotFound;
import project.toy.api.exception.PostNotFound;
import project.toy.api.repository.CommentRepository;
import project.toy.api.repository.MemberRepository;
import project.toy.api.repository.PostRepository;
import project.toy.api.request.CommentCreate;
import project.toy.api.response.CommentResponse;
import project.toy.api.response.CommonResponse;

import java.util.*;
import java.util.stream.Collectors;

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

        Comment parentComment = new Comment();
        if(commentCreate.getParentId() != null && commentCreate.getParentId() != 0){
            parentComment = commentRepository.findById(commentCreate.getParentId()).orElseThrow(ParentCommentNotFound::new);
        }

        Optional.ofNullable(parentComment).orElse(new Comment());
        Comment comment = Comment.builder()
                .comment(commentCreate.getComment())
                .member(findMember)
                .depthNumber(commentCreate.getDepthNumber())
                .parentComment(parentComment)
                .post(findPost).build();

        commentRepository.save(comment);

        return comment;
    }

    public HashMap<Long, CommentResponse> getComments(){
        List<Comment> comments = commentRepository.findAll();
        List<CommentResponse> commentResponses = comments.stream().map(CommentService::convertCommentValue).collect(Collectors.toList());
        HashMap<Long, CommentResponse> resultMap = new HashMap<>();
        commentResponses.forEach(comment -> {
            if(comment.getParentId() == null){
                resultMap.put(comment.getId(), comment);
            }
        });

        return resultMap;
    }

    public static CommentResponse convertCommentValue(Comment comment) {
        return CommentResponse.builder()
                .id(comment.getId())
                .parentId(comment.getParentComment() != null ? Optional.ofNullable(comment.getParentComment().getId()).orElse(0L) : null)
                .childComments(comment.getChildComment().stream().map(CommentService::convertCommentValue).collect(Collectors.toList()))
                .comment(comment.getComment())
                .createdBy(comment.getCreatedBy())
                .createdAt(comment.getCreatedAt())
                .build();
    }
}
