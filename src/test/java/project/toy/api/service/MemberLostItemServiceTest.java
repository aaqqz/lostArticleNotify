package project.toy.api.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.transaction.annotation.Transactional;
import project.toy.api.domain.LostCategory;
import project.toy.api.domain.MemberLostItem;
import project.toy.api.repository.MemberLostItemRepository;
import project.toy.api.repository.MemberRepository;
import project.toy.api.request.MemberLostItemCreate;
import project.toy.api.request.MemberLostItemEdit;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
@WithUserDetails("admin@naver.com")
class MemberLostItemServiceTest {

    @Autowired
    MemberLostItemService memberLostItemService;

    @Autowired
    MemberLostItemRepository memberLostItemRepository;

    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("회원분실물_등록")
    @Transactional
    void MLISave() {
        // given
        MemberLostItemCreate memberLostItemCreate = MemberLostItemCreate.builder()
                .category("핸드폰")
                .itemName("갤럭시 s23")
                .itemDetailInfo("버스에서")
                .build();

        // when
        memberLostItemService.save(memberLostItemCreate);

        // then
        assertThat(memberLostItemRepository.count()).isEqualTo(3L);
        MemberLostItem memberLostItem = memberLostItemRepository.findAll().get(2);
        assertThat(memberLostItem.getCategory()).isEqualTo(LostCategory.MOBILE);
        assertThat(memberLostItem.getItemName()).isEqualTo("갤럭시 s23");
        assertThat(memberLostItem.getItemDetailInfo()).isEqualTo("버스에서");
        assertThat(memberLostItem.getSendStatus()).isEqualTo("N");
    }

    @Test
    @DisplayName("회원분실물_수정")
    @Transactional
    void MLIEdit() {
        // given
        MemberLostItem memberLostItem = MemberLostItem.builder()
                .member(memberRepository.findById(1L).get())
                .category(LostCategory.BACKPACK)
                .itemName("배낭")
                .itemDetailInfo("버스에서")
                .build();
        memberLostItemRepository.save(memberLostItem);

        // when
        MemberLostItemEdit memberLostItemEdit = MemberLostItemEdit.builder()
                .category("파일")
                .itemName("파일이다")
                .itemDetailInfo("잃어버림")
                .build();
        memberLostItemService.edit(memberLostItem.getId(), memberLostItemEdit);

        // then
        assertThat(memberLostItemRepository.count()).isEqualTo(3L);
        assertThat(memberLostItem.getCategory()).isEqualTo(LostCategory.FILE);
        assertThat(memberLostItem.getItemName()).isEqualTo("파일이다");
        assertThat(memberLostItem.getItemDetailInfo()).isEqualTo("잃어버림");
        assertThat(memberLostItem.getSendStatus()).isEqualTo("N");
    }
}