package project.toy.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.toy.api.domain.Member;
import project.toy.api.repository.MemberRepository;
import project.toy.api.request.MemberCreate;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public void join(MemberCreate memberCreate) {
        Member member = Member.builder()
                .name(memberCreate.getName())
                .build();

        memberRepository.save(member);
    }

    public Member findOne(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("회원 없음")); // todo 상황에 맞는 exception 생성
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }
}
