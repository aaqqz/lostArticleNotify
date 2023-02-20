package project.toy.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.toy.api.domain.Member;
import project.toy.api.exception.InvalidLoginInformation;
import project.toy.api.repository.AuthRepository;
import project.toy.api.request.Login;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthRepository authRepository;

    public Member login(Login login) {
        Member member = authRepository.findByEmailAndPassword(login.getEmail(), login.getPassword())
                .orElseThrow(() -> new InvalidLoginInformation());

        return member;
    }
}
