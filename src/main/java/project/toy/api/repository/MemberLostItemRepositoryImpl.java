package project.toy.api.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import project.toy.api.domain.Member;

import static project.toy.api.domain.QMember.member;

@RequiredArgsConstructor
public class MemberLostItemRepositoryImpl implements MemberLostItemRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public String findByUserId(Long id) {
        Member member1 = queryFactory.select(Projections.fields(Member.class,
                        member.email))
                .from(member)
                .where(
                        member.id.eq(id)
                ).fetchOne();
        return member1.getEmail();
    }
}
