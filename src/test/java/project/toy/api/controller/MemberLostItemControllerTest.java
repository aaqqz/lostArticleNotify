package project.toy.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import project.toy.api.repository.MemberLostItemRepository;
import project.toy.api.repository.MemberRepository;
import project.toy.api.request.MemberLostItemCreate;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails("admin@naver.com")
class MemberLostItemControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MemberLostItemRepository memberLostItemRepository;

    @Autowired
    MemberRepository memberRepository;

    @Test
    @Transactional
    @DisplayName("회원분실물_등록")
    void MLISave() throws Exception {
        // given
        MemberLostItemCreate create = MemberLostItemCreate.builder()
                .category("핸드폰")
                .itemName("아이폰")
                .itemDetailInfo("흰색")
                .build();
        String json = objectMapper.writeValueAsString(create);

        // expected
        mockMvc.perform(post("/memberLostItem")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    @Transactional
    @DisplayName("회원분실물 등록_category null")
    void MLISave_categoryNull() throws Exception {
        // given
        MemberLostItemCreate create = MemberLostItemCreate.builder()
                .category(null)
                .itemName("아이폰")
                .itemDetailInfo("흰색")
                .build();
        String json = objectMapper.writeValueAsString(create);

        // expected
        mockMvc.perform(post("/memberLostItem")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.validation.category").value("카테고리를 선택해주세요."))
                .andDo(print());
    }

    @Test
    @Transactional
    @DisplayName("회원분실물 등록_itemName empty")
    void MLISave_itemNameEmpty() throws Exception {
        // given
        MemberLostItemCreate create = MemberLostItemCreate.builder()
                .category("장난감")
                .itemDetailInfo("흰색")
                .build();
        String json = objectMapper.writeValueAsString(create);

        // expected
        mockMvc.perform(post("/memberLostItem")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.validation.itemName").value("분실물 이름을 입력해주세요."))
                .andDo(print());
    }
}