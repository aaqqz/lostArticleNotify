package project.toy.api.scheduler.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SchedulerTest {

    @Autowired
    Scheduler scheduler;

    @Test
    void setLostItem() {
        scheduler.setLostItem();
        // todo fail valid
    }

}