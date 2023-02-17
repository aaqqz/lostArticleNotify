package project.toy.api.config;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import project.toy.api.config.data.MemberSession;
import project.toy.api.exception.JwtAuthFail;
import project.toy.api.exception.Unauthorized;

@Slf4j
@RequiredArgsConstructor
public class AuthResolver implements HandlerMethodArgumentResolver {

    private final JwtConfig jwtConfig;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        if (parameter.getParameterType().equals(MemberSession.class)) {
            return true;
        }
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        String jws = webRequest.getHeader("Authorization");
        if (jws == null || jws.equals("")) {
            throw new Unauthorized();
        }

        Jws<Claims> claims = null;
        try {
            claims = Jwts.parserBuilder()
                    .setSigningKey(jwtConfig.getJwtKey())
                    .build()
                    .parseClaimsJws(jws);

        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.error("잘못된 JWT 서명입니다.");
            throw new JwtAuthFail("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.error("만료된 JWT 토큰입니다.");
            throw new JwtAuthFail("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.error("지원되지 않는 JWT 토큰입니다.");
            throw new JwtAuthFail("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.error("JWT 토큰이 잘못되었습니다.");
            throw new JwtAuthFail("JWT 토큰이 잘못되었습니다.");
        }

        log.info("claims >>> {}", claims);
        Long memberId = Long.parseLong(claims.getBody().getSubject());
        String memberName = String.valueOf(claims.getBody().get("name"));
        int iat = (int) claims.getBody().get("iat");
        int exp = (int) claims.getBody().get("exp");

        return new MemberSession(memberId, memberName);
    }
}
