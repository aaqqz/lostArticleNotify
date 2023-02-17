package project.toy.api.scheduler.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import project.toy.api.repository.LostItemRepository;

@Slf4j
@SpringBootTest
class SchedulerServiceTest {

    @Autowired
    SchedulerService schedulerService;

    @Autowired
    LostItemRepository lostItemRepository;

    @Test
    void getLostItem() throws Exception {
        // given
        schedulerService.setLostItem();
        // when

        // then
    }

    @Test
    void test() {

    }

}