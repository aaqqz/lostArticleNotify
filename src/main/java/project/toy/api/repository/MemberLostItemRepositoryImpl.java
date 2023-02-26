package project.toy.api.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import project.toy.api.domain.MemberLostItem;

import javax.persistence.EntityManager;
import project.toy.api.domain.QMember;

import java.util.List;

import static project.toy.api.domain.QMember.member;
import static project.toy.api.domain.QMemberLostItem.memberLostItem;

@RequiredArgsConstructor
public class MemberLostItemRepositoryImpl implements MemberLostItemRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<MemberLostItem> findMemberLostItemFetchJoin() {

        List<MemberLostItem> result = queryFactory
                .selectFrom(memberLostItem)
                .join(memberLostItem.member, member).fetchJoin()
                .fetch();

        return result;
    }
}
