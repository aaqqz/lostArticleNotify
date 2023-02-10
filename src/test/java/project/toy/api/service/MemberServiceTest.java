package project.toy.api.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import project.toy.api.domain.Member;
import project.toy.api.repository.MemberRepository;
import project.toy.api.request.MemberCreate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @BeforeEach
    void clean() {
        memberRepository.deleteAll();
    }

    @Test
    void 회원_등록 () {
        // given
        MemberCreate memberCreate= MemberCreate.builder()
                .name("member")
                .build();

        // when
        memberService.join(memberCreate);

        // then
        assertThat(memberRepository.count()).isEqualTo(1L);
        Member member = memberRepository.findAll().get(0);
        assertThat(member.getName()).isEqualTo("member");
    }

    @Test
    void 회원_단건_조회() {
        //given
        Member member = Member.builder()
                .name("member")
                .build();
        memberRepository.save(member);

        //when
        Member findMember = memberService.findOne(member.getId());

        //then
        assertThat(findMember).isNotNull();
        assertThat(memberRepository.count()).isEqualTo(1L);
        assertThat(findMember.getId()).isEqualTo(1L);
        assertThat(findMember.getName()).isEqualTo("member");
    }

    @Test
    void 회원_단건_조회_에러() {
        //given

        //when

        //then
    }
}