package project.toy.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

//@Profile("local")
@Component
@RequiredArgsConstructor
public class InitController {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.init();
    }

    @Component
    static class InitService {

        @PersistenceContext
        private EntityManager em;

        @Transactional
        public void init() {
//            for (int i = 1; i <= 35; i++) {
//                Post post = Post.builder()
//                        .title("제목-" + i)
//                        .content("내용-" + i)
//                        .build();
//                em.persist(post);
//
//                Comment comment = Comment.builder()
//                                .comment("제목-" + i)
//                                .build();
//
//                em.persist(comment);
//            }
        }
    }
}
