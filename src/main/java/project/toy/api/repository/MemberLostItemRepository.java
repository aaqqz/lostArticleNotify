package project.toy.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.toy.api.domain.LostItem;

public interface MemberLostItemRepository extends JpaRepository<LostItem, String> {

}
