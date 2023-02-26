package project.toy.api.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import project.toy.api.domain.LostItem;
import project.toy.api.domain.LostStatus;
import project.toy.api.domain.MemberLostItem;

import java.util.List;

import static project.toy.api.domain.QLostItem.lostItem;

@RequiredArgsConstructor
public class LostItemRepositoryImpl implements LostItemRepositoryCustom{

    private final JPAQueryFactory queryFactory;
    @Override
    public List<LostItem> findLostItem(MemberLostItem memberItem) {
        List<LostItem> result = queryFactory.select(Projections.fields(LostItem.class,
                        lostItem.id,
                        lostItem.itemName,
                        lostItem.category,
                        lostItem.status,
                        lostItem.itemDetailInfo))
                .from(lostItem)
                .where(
                        lostItem.status.ne(LostStatus.RECEIVE),
                        lostItem.category.eq(memberItem.getCategory()),
                        lostItem.itemName.contains(memberItem.getItemName()),
                        lostItem.itemDetailInfo.contains(memberItem.getItemDetailInfo())
                ).fetch();

        return result;
    }
}
