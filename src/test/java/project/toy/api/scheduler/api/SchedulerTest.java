package project.toy.api.scheduler.api;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import project.toy.api.domain.LostItem;
import project.toy.api.domain.MemberLostItem;
import project.toy.api.repository.LostItemRepository;
import project.toy.api.repository.MemberLostItemRepository;

import java.util.List;

@Slf4j
@SpringBootTest
class SchedulerTest {

    @Autowired
    Scheduler scheduler;

    @Autowired
    LostItemRepository lostItemRepository;

    @Autowired
    MemberLostItemRepository memberLostItemRepository;

    @Test
    @DisplayName("분실물 api call")
    void apiCall() {
        // given
        scheduler.setLostItem();

        // when
        List<LostItem> result = lostItemRepository.findAll();

        // then
        Assertions.assertThat(result.size()).isGreaterThan(0);
    }

    @Test
    @DisplayName("매칭된 분실물 emailSend")
    void sendEmail() {
        // given
        scheduler.setLostItem();

        // when
        scheduler.sendEmail();

        // then
        List<MemberLostItem> findMemberLostItems = memberLostItemRepository.findAll();

        for (MemberLostItem findMemberLostItem : findMemberLostItems) {
            System.out.println("findMemberLostItem = " + findMemberLostItem);
        }
    }
}