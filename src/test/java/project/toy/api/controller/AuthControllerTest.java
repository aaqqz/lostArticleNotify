package project.toy.api.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import project.toy.api.request.Login;

import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AuthControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    public static String token;

    @BeforeAll
    void makeToken() throws Exception {
        // given
        Login login = Login.builder()
                .id("user@naver.com")
                .password("1234")
                .build();
        String json = objectMapper.writeValueAsString(login);

        // expected
        MvcResult result = mockMvc.perform(post("/auth/login")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String resultToken = result.getResponse().getContentAsString();
        Map<String, String> map = objectMapper.readValue(resultToken, new TypeReference<>() {});
        token = map.get("token");
    }

    @Test
    @DisplayName("로그인 성공")
    void authLogin() throws Exception {

        mockMvc.perform(post("/authX")
                        .contentType(APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andDo(print());
        System.out.println("dddddddddddd");
    }

    @Test
    @DisplayName("로그인 성공2")
    void authLogin3() throws Exception {

        mockMvc.perform(post("/authX")
                        .contentType(APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andDo(print());
        System.out.println("1111111");
    }
    @Test
    @DisplayName("로그인 성공1")
    void authLogin2() throws Exception {

        mockMvc.perform(post("/authX")
                        .contentType(APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andDo(print());
        System.out.println("6666666");
    }


}