package project.toy.api.controller;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import project.toy.api.config.JwtConfig;
import project.toy.api.config.data.MemberSession;
import project.toy.api.config.jwt.JwtTokenProvider;
import project.toy.api.domain.Member;
import project.toy.api.request.Login;
import project.toy.api.response.CommonResponse;
import project.toy.api.response.SessionResponse;
import project.toy.api.response.Token;
import project.toy.api.service.AuthService;

import javax.validation.Valid;
import java.util.Date;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtConfig jwtConfig;

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @PostMapping("/auth/login")
    public SessionResponse login(@RequestBody @Valid Login login){
        Member member = authService.login(login);

        Claims claims = Jwts.claims()
                .setSubject(String.valueOf(member.getId()));
        claims.put("name", member.getName());

        String jwt = Jwts.builder()
                .setSubject(String.valueOf(member.getId()))
                .setClaims(claims) // 정보 저장
                .signWith(Keys.hmacShaKeyFor(jwtConfig.getJwtKey()))
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + 86400000))
                .compact();

        return new SessionResponse(jwt);
    }

    // jwt test
    @PostMapping("/jwtUse")
    public MemberSession jwtUse(MemberSession userSession) {
        log.info("jwtUse >>>>>>>>>>>>>>>>>");
        log.info("userSession={}", userSession.toString());
        return userSession;
    }
    @PostMapping("/jwtDisUse")
    public String jwtDisUse() {
        log.info("jwtDisUse >>>>>>>>>>>>>>>>>");
        return "jwtDisUse";
    }

    @PostMapping("/authO")
    public String authO() {
        log.info("authO >>>>>>>>>>>>>>>>>");
        return "authO";
    }
    @PostMapping("/authX")
    public String authX() {
        log.info("authX >>>>>>>>>>>>>>>>>");
        return "authX";
    }

    @PostMapping("/auth/login2")
    public ResponseEntity<Token> login2(@RequestBody @Valid Login login) {

        // 1. Login ID/PW 를 기반으로 Authentication 객체 생성
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword());

        // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
        // authenticate 매서드가 실행될 때 CustomMemberDetailsService 에서 만든 loadUserByUsername 메서드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenProvider.createToken(authentication);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + jwt);

        return new ResponseEntity<>(new Token(jwt), httpHeaders, HttpStatus.OK);
    }
}
