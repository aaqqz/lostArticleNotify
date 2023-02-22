package project.toy.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.toy.api.config.security.SecurityUtils;
import project.toy.api.domain.Member;
import project.toy.api.domain.Post;
import project.toy.api.exception.MemberNotFound;
import project.toy.api.exception.PostNotFound;
import project.toy.api.repository.MemberRepository;
import project.toy.api.repository.PostRepository;
import project.toy.api.request.PostCreate;
import project.toy.api.request.PostEdit;
import project.toy.api.request.PostSearch;
import project.toy.api.response.PostResponse;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void write(PostCreate postCreate) {
        Long memberId = SecurityUtils.currentMemberId();
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFound());

        Post post = Post.builder()
                .title(postCreate.getTitle())
                .content(postCreate.getContent())
                .member(member)
                .build();
        postRepository.save(post);
    }

    public PostResponse get(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFound());

        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .createdBy(post.getCreatedBy())
                .build();
    }

    public Page<PostResponse> search(PostSearch postSearch, Pageable pageable) {
        return postRepository.search(postSearch, pageable);
    }

    @Transactional
    public void edit(Long id, PostEdit postEdit) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFound());

        post.edit(postEdit.getTitle(), postEdit.getContent());
    }

    @Transactional
    public void delete(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFound());

        postRepository.delete(post);
    }
}
