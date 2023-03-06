package project.toy.api.repository;

import project.toy.api.domain.MemberLostItem;
import project.toy.api.scheduler.vo.MatchingItemVO;
import project.toy.api.scheduler.vo.SendMailVO;

import java.util.List;

public interface MemberLostItemRepositoryCustom {
    List<MemberLostItem> findMemberLostItemFetchJoin();

    void memberLostItemSendStatusY(MatchingItemVO matchingItemVO);
}
