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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import project.toy.api.domain.Member;
import project.toy.api.repository.MemberRepository;
import project.toy.api.request.MemberCreate;
import project.toy.api.service.MemberService;

import java.util.Optional;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
class MemberControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @BeforeEach
    void clean() {
        Optional<Member> testMember = memberRepository.findByEmail("join@join.com");

        if (testMember.isPresent()) {
            memberRepository.delete(testMember.get());
        }
    }

    @Test
    @DisplayName("회원가입")
    void join() throws Exception {
        // given
        MemberCreate memberCreate = MemberCreate.builder()
                .name("회원가입이름")
                .email("join@join.com")
                .password("QWEqwe123!@#")
                .build();
        String json = objectMapper.writeValueAsString(memberCreate);

        // expected
        mockMvc.perform(MockMvcRequestBuilders.post("/member")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}