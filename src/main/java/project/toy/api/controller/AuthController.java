package project.toy.api.controller;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import project.toy.api.config.JwtConfig;
import project.toy.api.request.Login;
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
    public void login(@RequestBody @Valid Login login){
        Long memberId = authService.login(login);

        Jwts.builder()
                .setSubject(String.valueOf(memberId))
                .signWith(Keys.hmacShaKeyFor(jwtConfig.getJwtKey()))
                .setIssuedAt(new Date(new Date().getTime() + 86400000))
                .compact();

        // return null // todo jwt 토큰 반환부터
    }
}
