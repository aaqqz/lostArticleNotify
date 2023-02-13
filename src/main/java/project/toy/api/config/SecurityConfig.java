package project.toy.api.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests() // HttpServletRequest 를 사용하는 요청들에 대한 접근제한을 설정하겠다
                .antMatchers("/hello")
                .permitAll() // .antMatchers().permitAll() 인증없이 접급 허용
                .anyRequest().authenticated(); // .anyRequest().authenticated() 나머지 요청들은 모드 인증되어야 한다
        return http.build();
    }
}
