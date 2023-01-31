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

import static org.junit.jupiter.api.Assertions.*;

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
        assertEquals(1L, postRepository.count());
        Post post = postRepository.findAll().get(0);
        assertEquals("제목", post.getTitle());
        assertEquals("내용", post.getContent());
        assertNotNull(post.getCreatedBy());
        assertNotNull(post.getCreatedAt());
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
        assertNotNull(postResponse);
        assertEquals(1L, postRepository.count());
        assertEquals("제목", postResponse.getTitle());
        assertEquals("내용", postResponse.getContent());
        assertNotNull(postResponse.getCreatedBy());
        assertNotNull(postResponse.getCreatedAt());
    }
    
    @Test
    @DisplayName("게시글 단건 조회(게시글 없음)")
    void postGetNone() {
        //expected
        assertThrows(PostNotFound.class, () -> postService.get(1L));
    }

    @Test
    @DisplayName("게시글 복수 조회")
    void postGetList() {
        PostSearch postSearch = new PostSearch();
        List<Post> list = postService.getList(postSearch);
    }
}