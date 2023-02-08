package project.toy.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
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
import project.toy.api.request.PostEdit;
import project.toy.api.response.PostResponse;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    @DisplayName("게시글 등록_제목 필수값")
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
    @DisplayName("게시글 등록_내용 필수값")
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
    @DisplayName("게시글 등록_금지어 작성")
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
    @DisplayName("게시글 작성_DB insert")
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
        assertThat(postRepository.count()).isEqualTo(1L);
        Post post = postRepository.findAll().get(0);
        assertThat(post.getTitle()).isEqualTo("제목");
        assertThat(post.getContent()).isEqualTo("내용");

    }

    @Test
    @DisplayName("게시글 단건 조회")
    void postGet() throws Exception {
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
    @DisplayName("게시글 단건 조회_게시글 없음")
    void postGetNone() throws Exception {
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

    // todo 페이징 조회

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
        String json = objectMapper.writeValueAsString(postEdit);

        // expected
        mockMvc.perform(patch("/post/{postId}", post.getId())
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("게시글 수정_제목 null")
    void postEditNoTitle() throws Exception {
        // todo testcase 작성
    }

}