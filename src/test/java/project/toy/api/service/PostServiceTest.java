package project.toy.api.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.context.support.WithUserDetails;
import project.toy.api.domain.Post;
import project.toy.api.exception.PostNotFound;
import project.toy.api.repository.PostRepository;
import project.toy.api.request.PostCreate;
import project.toy.api.request.PostEdit;
import project.toy.api.request.PostSearch;
import project.toy.api.response.PostResponse;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
@SpringBootTest
@WithUserDetails("admin@naver.com")
class PostServiceTest {

    @Autowired
    PostService postService;

    @Autowired
    PostRepository postRepository;

    @BeforeEach
    void clean() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("게시글 등록")
    void postWrite() {
        // given
        PostCreate postCreate = PostCreate.builder()
                .title("제목")
                .content("내용")
                .build();

        // when
        postService.write(postCreate);

        // then
        assertThat(postRepository.count()).isEqualTo(1L);
        Post post = postRepository.findAll().get(0);
        assertThat(post.getTitle()).isEqualTo("제목");
        assertThat(post.getContent()).isEqualTo("내용");
        assertThat(post.getCreatedBy()).isEqualTo("nmAdmin");
        assertThat(post.getCreatedAt()).isNotNull();
    }

    @Test
    @DisplayName("게시글 단건 조회")
    void postGet() {
        // given
        Post post = Post.builder()
                .title("제목")
                .content("내용")
                .build();
        postRepository.save(post);

        // when
        PostResponse postResponse = postService.get(post.getId());

        // then
        assertThat(postResponse).isNotNull();
        assertThat(postRepository.count()).isEqualTo(1L);
        assertThat(post.getTitle()).isEqualTo("제목");
        assertThat(post.getContent()).isEqualTo("내용");
        assertThat(post.getCreatedBy()).isEqualTo("nmAdmin");
        assertThat(post.getCreatedAt()).isNotNull();
    }

    @Test
    @DisplayName("게시글 단건 조회_게시글 없음")
    void postGetNone() {
        //expected
        assertThatThrownBy(() -> postService.get(1L))
                .isInstanceOf(PostNotFound.class)
                .hasMessageContaining("존재하지 않는 글입니다.");
    }

    @Test
    @DisplayName("게시글 페이징 조회")
    void postSearch() {
        // given
        List<Post> posts = IntStream.range(0, 30)
                .mapToObj(i -> Post.builder()
                        .title("제목-" + i)
                        .content("내용-" + i)
                        .build()
                ).collect(Collectors.toList());
        postRepository.saveAll(posts);

        Pageable pageable = PageRequest.of(0, 3);

        PostSearch postSearch = PostSearch.builder()
                .title("")
                .content("")
                .build();
        // when
        Page<PostResponse> result = postService.search(postSearch, pageable);

        // then
//        List<PostResponse> result = PagePostResponse.stream()
//                .map(post ->
//                        PostResponse.builder()
//                                .id(post.getId())
//                                .title(post.getTitle())
//                                .content(post.getContent())
//                                .createdBy(post.getCreatedBy())
//                                .createdAt(post.getCreatedAt())
//                                .build())
//                .collect(Collectors.toList());
        log.info("result={}", result.getContent());
        assertThat(result.getSize()).isEqualTo(3);
        assertThat(result.getContent()).extracting("title").containsExactly("제목-29", "제목-28", "제목-27");

    }

    @Test
    @DisplayName("게시글 수정")
    void postEdit() throws Exception {
        // given
        Post post = Post.builder()
                .title("제목")
                .content("내용")
                .build();
        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title("수정 제목")
                .content("수정 내용")
                .build();

        // when
        Thread.sleep(1000);
        postService.edit(post.getId(), postEdit);

        // then
        Post changedPost = postRepository.findById(post.getId())
                .orElseThrow(() -> new IllegalArgumentException("글이 존재하지 않습니다."));
        assertThat(changedPost.getTitle()).isEqualTo("수정 제목");
        assertThat(changedPost.getContent()).isEqualTo("수정 내용");
        assertThat(post.getCreatedAt()).isLessThan(changedPost.getLastModifiedAt());
    }

    @Test
    @DisplayName("게시글 수정_제목 null")
    void postEditNoTitle() throws Exception {
        // given
        Post post = Post.builder()
                .title("제목")
                .content("내용")
                .build();
        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title(null)
                .content("수정 내용")
                .build();

        // when
        Thread.sleep(1000);
        postService.edit(post.getId(), postEdit);

        // then
        Post changedPost = postRepository.findById(post.getId())
                .orElseThrow(() -> new IllegalArgumentException("글이 존재하지 않습니다."));
        assertThat(changedPost.getTitle()).isEqualTo("제목");
        assertThat(changedPost.getContent()).isEqualTo("수정 내용");
        assertThat(post.getCreatedAt()).isLessThan(changedPost.getLastModifiedAt());
    }

    @Test
    @DisplayName("게시글 수정_내용 null")
    void postEditNoContent() throws Exception {
        // given
        Post post = Post.builder()
                .title("제목")
                .content("내용")
                .build();
        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title("수정 제목")
                .content(null)
                .build();

        // when
        Thread.sleep(1000);
        postService.edit(post.getId(), postEdit);

        // then
        Post changedPost = postRepository.findById(post.getId())
                .orElseThrow(() -> new IllegalArgumentException("글이 존재하지 않습니다."));
        assertThat(changedPost.getTitle()).isEqualTo("수정 제목");
        assertThat(changedPost.getContent()).isEqualTo("내용");
        assertThat(post.getCreatedAt()).isLessThan(changedPost.getLastModifiedAt());
    }

    @Test
    @DisplayName("게시글 수정_존재하지 않는 게시글")
    void postEditNoPost() throws Exception {
        // given
        Post post = Post.builder()
                .title("제목")
                .content("내용")
                .build();
        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title("수정 제목")
                .content("수정 내용")
                .build();

        // expected
        assertThatThrownBy(() -> postService.edit(post.getId() + 1L, postEdit))
                .isInstanceOf(PostNotFound.class)
                .hasMessageContaining("존재하지 않는 글입니다.");
    }

    @Test
    @DisplayName("게시글 삭제")
    void postDelete() {
        // given
        Post post = Post.builder()
                .title("제목")
                .content("내용")
                .build();
        postRepository.save(post);

        // when
        postService.delete(post.getId());

        // then
        assertThat(postRepository.count()).isEqualTo(0);
    }

    @Test
    @DisplayName("게시글 삭제_존재하지 않는 게시글")
    void deleteNoPost() {
        // given
        Post post = Post.builder()
                .title("제목")
                .content("내용")
                .build();
        postRepository.save(post);

        // expected
        assertThatThrownBy(() -> postService.delete(post.getId() + 1L))
                .isInstanceOf(PostNotFound.class)
                .hasMessageContaining("존재하지 않는 글입니다.");
    }
}