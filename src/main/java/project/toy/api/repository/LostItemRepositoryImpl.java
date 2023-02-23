package project.toy.api.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import project.toy.api.domain.LostItem;
import project.toy.api.domain.MemberLostItem;
import project.toy.api.vo.MemberLostItemVO;

import java.util.List;

import static project.toy.api.domain.QLostItem.lostItem;

@RequiredArgsConstructor
public class LostItemRepositoryImpl implements LostItemRepositoryCustom{

    private final JPAQueryFactory queryFactory;
    @Override
    public List<LostItem> findLostItem(MemberLostItemVO memberItem) {
        List<LostItem> result = queryFactory.select(Projections.fields(LostItem.class,
                        lostItem.id,
                        lostItem.itemName,
                        lostItem.category,
                        lostItem.status,
                        lostItem.itemDetailInfo))
                .from(lostItem)
                .where(
                        lostItem.itemName.contains(memberItem.getItemName()),
                        lostItem.itemDetailInfo.contains(memberItem.getItemDetailInfo()),
                        lostItem.category.eq(memberItem.getCategory())
                ).fetch();

        return result;
    }
}
