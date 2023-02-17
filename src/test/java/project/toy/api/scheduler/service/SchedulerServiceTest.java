package project.toy.api.scheduler.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import project.toy.api.domain.LostItem;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SchedulerServiceTest {

    @Autowired
    SchedulerService schedulerService;

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