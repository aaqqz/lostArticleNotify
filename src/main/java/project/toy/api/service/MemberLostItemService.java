package project.toy.api.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.toy.api.common.Common;
import project.toy.api.domain.Member;
import project.toy.api.domain.MemberLostItem;
import project.toy.api.exception.MemberNotFound;
import project.toy.api.repository.MemberRepository;
import project.toy.api.request.MemberLostItemCreate;

@Service
@RequiredArgsConstructor
public class MemberLostItemService {

    private final MemberRepository memberRepository;

    public void save(MemberLostItemCreate memberLostItemCreate) {
        Member findMember = memberRepository.findById(memberLostItemCreate.getMemberId())
                .orElseThrow(MemberNotFound::new);

        MemberLostItem memberLostItem = MemberLostItem.builder()
                .member(findMember)
                .category(Common.getLostCategory(memberLostItemCreate.getCategory()))
                .itemName(memberLostItemCreate.getItemName())
                .itemDetailInfo(memberLostItemCreate.getItemDetailInfo())
                .build();
    }
}
