package project.toy.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.toy.api.config.security.SecurityUtils;
import project.toy.api.domain.LostCategory;
import project.toy.api.domain.Member;
import project.toy.api.domain.MemberLostItem;
import project.toy.api.exception.MemberLostItemNotFound;
import project.toy.api.exception.MemberNotFound;
import project.toy.api.repository.MemberLostItemRepository;
import project.toy.api.repository.MemberRepository;
import project.toy.api.request.MemberLostItemCreate;
import project.toy.api.request.MemberLostItemEdit;

import static project.toy.api.config.security.SecurityUtils.currentMemberId;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberLostItemService {

    private final MemberRepository memberRepository;
    private final MemberLostItemRepository memberLostItemRepository;

    @Transactional
    public void save(MemberLostItemCreate memberLostItemCreate) {
        Long memberId = SecurityUtils.currentMemberId();
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFound::new);

        MemberLostItem memberLostItem = MemberLostItem.builder()
                .member(findMember)
                .category(LostCategory.getKey(memberLostItemCreate.getCategory()))
                .itemName(memberLostItemCreate.getItemName())
                .itemDetailInfo(memberLostItemCreate.getItemDetailInfo())
                .build();

        memberLostItemRepository.save(memberLostItem);
    }

    @Transactional
    public MemberLostItem edit(Long id, MemberLostItemEdit editData) {

        MemberLostItem findMemberLostItem = memberLostItemRepository.findById(id)
                .orElseThrow(MemberLostItemNotFound::new);

        if (!findMemberLostItem.getMember().getId().equals(currentMemberId())) {
            throw new IllegalStateException("회원 정보가 일치하지 않습니다.");
        }

        findMemberLostItem.edit(LostCategory.getKey(editData.getCategory()), editData.getItemName(), editData.getItemDetailInfo());
        return findMemberLostItem;
    }
}
