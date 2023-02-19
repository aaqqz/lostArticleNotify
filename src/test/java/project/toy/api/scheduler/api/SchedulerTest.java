package project.toy.api.scheduler.api;

import lombok.Data;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SchedulerTest {

    @Autowired
    Scheduler scheduler;


    @Test
    @DisplayName("lostItemApi data setting")
    void setLostItem() {
        scheduler.setLostItem();
    }

}