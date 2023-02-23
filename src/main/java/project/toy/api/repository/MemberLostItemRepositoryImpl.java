package project.toy.api.repository;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import project.toy.api.domain.Member;
import project.toy.api.domain.MemberLostItem;
import project.toy.api.vo.MemberLostItemVO;

import javax.persistence.EntityManager;

import java.util.List;

import static project.toy.api.domain.QMember.member;
import static project.toy.api.domain.QMemberLostItem.memberLostItem;

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

    @Override
    public List<MemberLostItemVO> findMemberLostItems() {
        return  query.select(Projections.fields(MemberLostItemVO.class,
                                member.email, member.id)).from(memberLostItem).join(memberLostItem.member).fetchJoin()
                        .fetch();
    }
}
