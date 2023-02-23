package project.toy.api.repository;

import project.toy.api.domain.LostItem;
import project.toy.api.domain.MemberLostItem;
import project.toy.api.vo.MemberLostItemVO;

import java.util.List;

public interface LostItemRepositoryCustom {
    List<LostItem> findLostItem(MemberLostItem memberItem);
}
