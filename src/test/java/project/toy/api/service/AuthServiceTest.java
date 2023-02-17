package project.toy.api.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import project.toy.api.domain.Member;
import project.toy.api.repository.AuthRepository;
import project.toy.api.repository.MemberRepository;
import project.toy.api.request.Login;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuthServiceTest {

    @Autowired
    AuthService authService;

    @Autowired
    AuthRepository authRepository;

    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("로그인시_회원id_return")
    void login() {
        // given
        Member member = Member.builder()
                .email("sswwx1@naver.com")
                .name("hk")
                .password("1234")
                .build();
        memberRepository.save(member);

        Login login = Login.builder()
                .id(member.getEmail())
                .password(member.getPassword())
                .build();

        // when
        Member loginMember = authService.login(login);

        // then
        Assertions.assertThat(loginMember.getId()).isEqualTo(member.getId());
    }


}