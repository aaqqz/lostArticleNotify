package project.toy.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.toy.api.domain.Member;
import project.toy.api.exception.MemberExist;
import project.toy.api.repository.MemberRepository;
import project.toy.api.request.MemberCreate;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Member join(MemberCreate memberCreate) {

        boolean memberExist = memberRepository.memberExist(memberCreate.getEmail());
        if (memberExist) {
            throw new MemberExist();
        }

        Member joinMember = Member.builder()
                .name(memberCreate.getName())
                .email(memberCreate.getEmail())
                .password(passwordEncoder.encode(memberCreate.getPassword()))
                .roleType("user")
                .build();

        return memberRepository.save(joinMember);
    }
}
