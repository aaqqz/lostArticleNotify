package project.toy.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.toy.api.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
