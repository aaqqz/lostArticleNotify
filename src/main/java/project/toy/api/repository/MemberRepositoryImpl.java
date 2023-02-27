package project.toy.api.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import project.toy.api.domain.Member;

import java.util.List;

import static project.toy.api.domain.QMember.member;

@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public boolean memberExist(String email) {

        boolean exist = queryFactory
                .selectOne()
                .from(member)
                .where(member.email.eq(email))
                .fetchOne() != null; // 값이 없으면 0이 아니라 null 반환

        return exist;
    }
}
