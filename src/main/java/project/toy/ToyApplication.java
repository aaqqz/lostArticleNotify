package project.toy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.web.config.PageableHandlerMethodArgumentResolverCustomizer;
import project.toy.api.config.JwtConfig;

import java.util.Optional;
import java.util.UUID;

@EnableConfigurationProperties(JwtConfig.class)
@EnableJpaAuditing // BaseEntity 설정
@SpringBootApplication
public class ToyApplication {

	public static void main(String[] args) {
		SpringApplication.run(ToyApplication.class, args);
	}

	@Bean
	public AuditorAware<String> auditorProvider(){
		// todo 로그인 개발후 id 값 생성으로 변경
		return () -> Optional.of(UUID.randomUUID().toString()); // test 이기 때문에 작성 -> 스프링 시큐리티 등 에서 id 넣는다
	}
}
