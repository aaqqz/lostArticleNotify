package project.toy.api.repository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import project.toy.api.domain.Member;

import javax.persistence.EntityManager;

import static project.toy.api.domain.QMember.member;

@RequiredArgsConstructor
public class MemberLostItemRepositoryImpl implements MemberLostItemRepositoryCustom{

    private final JPAQueryFactory query;

    @Override
    public String findByUserId(Long id) {
        Member member1 = query.select(Projections.fields(Member.class,
                member.email))
                .from(member)
                .where(
                        member.id.eq(id)
                ).fetchOne();
        return member1.getEmail();
    }
}
