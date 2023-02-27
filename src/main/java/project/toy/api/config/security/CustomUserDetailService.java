package project.toy.api.config.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import project.toy.api.config.security.data.CustomMemberDetails;
import project.toy.api.domain.Member;
import project.toy.api.exception.MemberNotFound;
import project.toy.api.repository.MemberRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String memberEmail) throws UsernameNotFoundException {
        log.info("memberEmail={}", memberEmail);
        CustomMemberDetails customMember = memberRepository.findByEmail(memberEmail)
                .map(m -> createMember(m))
                .orElseThrow(() -> new MemberNotFound());

        return customMember;
    }

    // Security User 정보를 생성한다.
    private CustomMemberDetails createMember(Member member) {
        return CustomMemberDetails.builder()
                .id(member.getId())
                .name(member.getName())
                .email(member.getEmail())
                .password(member.getPassword())
                .roleType(member.getRoleType())
                .build();

    }
}
