package project.toy.api.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import project.toy.api.config.data.MemberSession;
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

        Jws<Claims> claims = Jwts.parserBuilder()
                .setSigningKey(jwtConfig.getJwtKey())
                .build()
                .parseClaimsJws(jws);

        log.info("claims >>> {}", claims);
        Long memberId = Long.parseLong(claims.getBody().getSubject());
        String memberName = String.valueOf(claims.getBody().get("name"));

        log.info("claims.memberId >>> {}", memberId);
        log.info("claims.name >>> {}", memberName);
        return new MemberSession(memberId, memberName);
    }
}
