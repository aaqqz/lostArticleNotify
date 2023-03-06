package project.toy.api.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import project.toy.api.domain.LostCategory;
import project.toy.api.domain.LostStatus;
import project.toy.api.domain.MemberLostItem;
import project.toy.api.scheduler.vo.MatchingItemVO;

import java.util.List;

import static com.querydsl.core.types.dsl.Expressions.asNumber;
import static org.springframework.util.StringUtils.hasText;
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
                        categoryEquals(memberLostItem.getCategory()),
                        itemNameLike(memberLostItem.getItemName()),
                        itemDetailInfo(memberLostItem.getItemDetailInfo())
                ).fetch();

        return result;
    }

    private BooleanExpression categoryEquals(LostCategory category) {
        return hasText(String.valueOf(category)) ? lostItem.category.eq(category) : null;
    }

    private BooleanExpression itemNameLike(String itemName) {
        return hasText(itemName) ? lostItem.itemName.contains(itemName) : null;
    }

    private BooleanExpression itemDetailInfo(String itemDetailInfo) {
        return hasText(itemDetailInfo) ? lostItem.itemDetailInfo.contains(itemDetailInfo) : null;
    }
}
