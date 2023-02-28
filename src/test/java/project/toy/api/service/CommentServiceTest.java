package project.toy.api.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import project.toy.api.domain.Comment;
import project.toy.api.domain.Member;
import project.toy.api.exception.MemberExist;
import project.toy.api.repository.MemberRepository;
import project.toy.api.request.CommentCreate;
import project.toy.api.request.MemberCreate;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class CommentServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    CommentService commentService;

    @BeforeEach
    void clean() {
        Optional<Member> testMember = memberRepository.findByEmail("join@join.com");

        if (testMember.isPresent()){
            memberRepository.delete(testMember.get());
        }
    }

    @Test
    @DisplayName("댓글 저장 기능 확인")
    public void saveComment() {
        //given
        CommentCreate commentCreate = CommentCreate.builder()
                .comment("대댓글 남기기")
                .depthNum(1)
                .postId(2L)
                .memberId(1L)
                .parentId(1L)
                .build();
        //when
        Comment comment = commentService.write(commentCreate);

        //then
        assertThat(comment).isNotNull();
    }

    @Test
    @DisplayName("댓글 조회 기능 확인")
    public void getComment() {

    }


}