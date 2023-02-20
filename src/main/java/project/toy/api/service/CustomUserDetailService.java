package project.toy.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.toy.api.domain.Member;
import project.toy.api.exception.MemberNotFound;
import project.toy.api.repository.MemberRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String memberEmail) throws UsernameNotFoundException {
        log.info("memberEmail={}", memberEmail);
        UserDetails member = memberRepository.findByEmail(memberEmail)
                .map(m -> createMember(m))
                .orElseThrow(() -> new MemberNotFound());

        return member;
    }

    // Security User 정보를 생성한다.
    private UserDetails createMember(Member member) {
        return User.builder()
                .username(member.getName())
                .password(passwordEncoder.encode(member.getPassword()))
                .roles(new String[]{member.getRoleType()})
                .build();

    }
}
