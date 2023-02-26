package project.toy.api.repository;

import project.toy.api.domain.LostItem;
import project.toy.api.domain.MemberLostItem;

import java.util.List;

public interface LostItemRepositoryCustom {
    List<LostItem> findLostItem(MemberLostItem memberLostItem);
}
