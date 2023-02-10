package project.toy.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.toy.api.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {
}
