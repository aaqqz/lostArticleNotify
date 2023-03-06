package project.toy.api.repository;

import com.querydsl.core.annotations.QueryProjection;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import project.toy.api.domain.LostItem;
import project.toy.api.domain.LostStatus;
import project.toy.api.domain.MemberLostItem;
import project.toy.api.scheduler.vo.MatchingItemVO;

import java.util.List;

import static com.querydsl.core.types.dsl.Expressions.*;
import static project.toy.api.domain.QLostItem.lostItem;

@RequiredArgsConstructor
public class LostItemRepositoryImpl implements LostItemRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<MatchingItemVO> findMatchingLostItem(MemberLostItem memberLostItem) {

        List<MatchingItemVO> result = queryFactory
                .select(Projections.fields(MatchingItemVO.class,
                        asNumber(memberLostItem.getId()).as("memberLostItemId"),
                        lostItem.status,
                        lostItem.category,
                        lostItem.itemName,
                        lostItem.itemDetailInfo,
                        lostItem.takePosition))
                .from(lostItem)
                .where(
                        lostItem.status.ne(LostStatus.RECEIVE),
                        lostItem.category.eq(memberLostItem.getCategory()),
                        lostItem.itemName.contains(memberLostItem.getItemName()),
                        lostItem.itemDetailInfo.contains(memberLostItem.getItemDetailInfo())
                ).fetch();

        return result;
    }
}
