package project.toy.api.repository;

import project.toy.api.domain.MemberLostItem;
import project.toy.api.scheduler.vo.MatchingItemVO;

import java.util.List;

public interface LostItemRepositoryCustom {
    List<MatchingItemVO> findMatchingLostItem(MemberLostItem memberLostItem);
}
