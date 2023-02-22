package project.toy.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.toy.api.domain.MemberLostItem;

public interface MemberLostItemRepository extends JpaRepository<MemberLostItem, String> {

}
