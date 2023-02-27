package project.toy.api.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import project.toy.api.domain.Member;
import project.toy.api.exception.MemberExist;
import project.toy.api.repository.MemberRepository;
import project.toy.api.request.MemberCreate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

//    @BeforeEach
//    void clean() {
//        memberRepository.deleteAll();
//    }

    @Test
    @DisplayName("회원가입")
    void join() {
        // given
        MemberCreate memberCreate = MemberCreate.builder()
                .name("회원가입 이름")
                .email("join@join.com")
                .password("QWEqwe123!@#")
                .build();

        // when
        Member savedMember = memberService.join(memberCreate);

        // then
        assertThat(savedMember.getName()).isEqualTo("회원가입 이름");
        assertThat(savedMember.getEmail()).isEqualTo("join@join.com");
        assertThat(passwordEncoder.matches("QWEqwe123!@#", savedMember.getPassword())).isTrue();
    }

    @Test
    @DisplayName("회원가입_이미 존재 하는 회원")
    void joinExist() {
        // given
        MemberCreate memberCreate = MemberCreate.builder()
                .name("이미 존재함")
                .email("admin@naver.com")
                .password("QWEqwe123!@#")
                .build();

        // expected
        assertThatThrownBy(() -> memberService.join(memberCreate))
                .isInstanceOf(MemberExist.class)
                .hasMessageContaining("동일한 email이 존재합니다.");
    }
}