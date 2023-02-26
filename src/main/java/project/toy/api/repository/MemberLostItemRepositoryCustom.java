package project.toy.api.repository;

import project.toy.api.domain.MemberLostItem;

import java.util.List;

public interface MemberLostItemRepositoryCustom {

    String findByUserId(Long id);

    List<MemberLostItem> findMemberLostItems();
}
