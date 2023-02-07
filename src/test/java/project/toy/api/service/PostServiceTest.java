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
import project.toy.api.request.PostSearch;
import project.toy.api.response.PostResponse;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

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
    @DisplayName("게시글 단건 조회(게시글 없음)")
    void postGetNone() {
        //expected
        assertThatThrownBy(() -> postService.get(1L))
                .isInstanceOf(PostNotFound.class)
                .hasMessageContaining("존재하지 않는 글입니다.");
    }

    @Test
    @DisplayName("게시글 복수 조회")
    void postGetList() {
        PostSearch postSearch = new PostSearch();
        List<Post> list = postService.getList(postSearch);
    }
}