package project.toy.api.repository;

import project.toy.api.domain.MemberLostItem;

import java.util.List;

public interface MemberLostItemRepositoryCustom {
    List<MemberLostItem> findMemberLostItemFetchJoin();
}
