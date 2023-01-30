package project.toy.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.toy.web.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {


}
