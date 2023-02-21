package project.toy.api.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;
import java.util.UUID;

@Configuration
public class QueryDslConfig {

    @PersistenceContext
    EntityManager em;

    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(em);
    }

    @Bean
    public AuditorAware<String> auditorProvider(){
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (null == authentication || !authentication.isAuthenticated()) {
//            return null;
//        }
//        Member member = (Member) authentication.getPrincipal();
//        return () -> Optional.of(member.getName()); // test 이기 때문에 작성 -> 스프링 시큐리티 등 에서 id 넣는다
        return () -> Optional.of(UUID.randomUUID().toString());
    }
}
