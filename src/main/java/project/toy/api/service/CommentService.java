package project.toy.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.toy.api.domain.Comment;
import project.toy.api.domain.Member;
import project.toy.api.domain.Post;
import project.toy.api.exception.PostNotFound;
import project.toy.api.repository.CommentRepository;
import project.toy.api.repository.MemberRepository;
import project.toy.api.repository.PostRepository;
import project.toy.api.request.CommentCreate;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;
    
    public void write(CommentCreate commentCreate) {

        Post findPost = postRepository.findById(commentCreate.getPostId())
                .orElseThrow(() -> new PostNotFound());

        Member findMember = memberRepository.findById(commentCreate.getMemberId())
                .orElseThrow();


    }
}