package project.toy.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import project.toy.api.domain.Post;
import project.toy.api.repository.PostRepository;
import project.toy.api.request.PostCreate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
class PostControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    PostRepository postRepository;

    @BeforeEach
    void clear() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("게시글 등록")
    void postWrite() throws Exception {
        // given
        PostCreate postCreate = PostCreate.builder()
                .title("제목")
                .content("내용")
                .build();

        String json = objectMapper.writeValueAsString(postCreate);

        // expected
        mockMvc.perform(post("/post")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("게시글 등록 (제목 필수값)")
    void postWriteNoneTitle() throws Exception {
        // given
        PostCreate postCreate = PostCreate.builder()
                .title("")
                .content("내용")
                .build();

        String json = objectMapper.writeValueAsString(postCreate);

        // expected
        mockMvc.perform(post("/post")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(jsonPath("$.validation.title").value("제목을 입력해주세요."))
                .andDo(print());
    }

    @Test
    @DisplayName("게시글 등록 (내용 필수값)")
    void postWriteNoneContent() throws Exception {
        // given
        PostCreate postCreate = PostCreate.builder()
                .title("제목")
                .content("")
                .build();

        String json = objectMapper.writeValueAsString(postCreate);

        // expected
        mockMvc.perform(post("/post")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(jsonPath("$.validation.content").value("내용을 입력해주세요."))
                .andDo(print());
    }


    @Test
    @DisplayName("게시글 등록 (금지어 작성)")
    void postWriteBanWord() throws Exception {
        PostCreate postCreate = PostCreate.builder()
                .title("금지어")
                .content("내용")
                .build();

        String json = objectMapper.writeValueAsString(postCreate);

        // expected
        mockMvc.perform(post("/post")
                .contentType(APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(jsonPath("$.validation.title").value("제목에 '금지어'를 포함할 수 없습니다."))
                .andDo(print());
    }

    @Test
    @DisplayName("게시글 작성 (DB insert)")
    void postInsertDB() throws Exception {
        // given
        PostCreate postCreate = PostCreate.builder()
                .title("제목")
                .content("내용")
                .build();

        String json = objectMapper.writeValueAsString(postCreate);

        // when
        mockMvc.perform(post("/post")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andDo(print());

        // then
        assertEquals(1L, postRepository.count());
        Post post = postRepository.findAll().get(0);
        assertEquals("제목", post.getTitle());
        assertEquals("내용", post.getContent());
    }

    @Test
    @DisplayName("게시글 단건 조회")
    void postGet() throws Exception{
        // given
        Post post = Post.builder()
                .title("제목")
                .content("내용")
                .build();
        postRepository.save(post);


        // expected
        mockMvc.perform(get("/post/{postId}", post.getId())
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("제목"))
                .andExpect(jsonPath("$.content").value("내용"))
                .andDo(print());
    }

    @Test
    @DisplayName("게시글 단건 조회(게시글 없음)")
    void postGetNone() throws Exception{
        // given
        Post post = Post.builder()
                .title("제목")
                .content("내용")
                .build();
        postRepository.save(post);


        // expected
        mockMvc.perform(get("/post/{postId}", post.getId() + 1L)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("404"))
                .andExpect(jsonPath("$.message").value("존재하지 않는 글입니다."))
                .andDo(print());
    }
}