package project.toy.api.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import project.toy.api.domain.Member;
import project.toy.api.repository.MemberRepository;
import project.toy.api.request.MemberCreate;

import static org.junit.jupiter.api.Assertions.*;

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
        assertEquals(1L, memberRepository.count());
        Member member = memberRepository.findAll().get(0);
        assertEquals("member", member.getName());
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
        assertNotNull(findMember);
        assertEquals(1L, memberRepository.count());
        assertEquals(1L, findMember.getId());
        assertEquals("member", findMember.getName());
    }

    @Test
    void 회원_단건_조회_에러() {
        //given

        //when

        //then
    }
}