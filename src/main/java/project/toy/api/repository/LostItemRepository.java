package project.toy.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.toy.api.domain.LostItem;

public interface LostItemRepository extends JpaRepository<LostItem, String> {
}
