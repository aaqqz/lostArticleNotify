package project.toy.api.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import project.toy.api.domain.Post;
import project.toy.api.exception.PostNotFound;
import project.toy.api.repository.PostRepository;
import project.toy.api.request.PostCreate;
import project.toy.api.request.PostEdit;
import project.toy.api.response.PostResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
@SpringBootTest
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
        assertThat(post.getCreatedAt()).isNotNull();
        assertThat(post.getCreatedBy()).isNotNull();
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
        assertThat(post.getCreatedAt()).isNotNull();
        assertThat(post.getCreatedBy()).isNotNull();
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
    @DisplayName("게시글 리스트 조회")
    void postSearch() {
        // todo 페이징 조회
        /*PostSearch postSearch = new PostSearch();
        List<Post> list = postService.getList(postSearch);*/
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
        assertThatThrownBy(() -> postService.edit(post.getId() +1L, postEdit))
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