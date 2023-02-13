package project.toy.api.controller;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import project.toy.api.config.JwtConfig;
import project.toy.api.config.data.MemberSession;
import project.toy.api.domain.Member;
import project.toy.api.request.Login;
import project.toy.api.response.SessionResponse;
import project.toy.api.service.AuthService;

import javax.validation.Valid;
import java.util.Date;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtConfig jwtConfig;

    @PostMapping("/auth/login")
    public SessionResponse login(@RequestBody @Valid Login login){
        Member member = authService.login(login);

        Claims claims = Jwts.claims()
                .setSubject(String.valueOf(member.getId()));
        claims.put("name", member.getName());

        String jwt = Jwts.builder()
//                .setSubject(String.valueOf(member.getId()))
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
}
