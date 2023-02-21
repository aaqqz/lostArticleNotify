package project.toy.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.toy.api.domain.LostItem;
import project.toy.api.domain.MemberLostItem;

import java.util.List;

public interface LostItemRepository extends JpaRepository<LostItem, String> {

}
