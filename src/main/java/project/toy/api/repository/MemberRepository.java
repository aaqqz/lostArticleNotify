package project.toy.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.toy.api.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {


}
