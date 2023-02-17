package project.toy.api.scheduler.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import project.toy.api.scheduler.service.SchedulerService;

@Slf4j
@Component
@RequiredArgsConstructor
public class Scheduler {

    /**********************************************************************************************
     *           *　　　　　　*　　　　　　*　　　　　　*　　　　　　*
     초(0-59)   분(0-59)　　시간(0-23)　　일(1-31)　　월(1-12)　　요일(0-7)
     각 별 위치에 따라 주기를 다르게 설정 할 수 있다.
     순서대로 초-분-시간-일-월-요일 순이다. 그리고 괄호 안의 숫자 범위 내로 별 대신 입력 할 수도 있다.
     요일에서 0과 7은 일요일이며, 1부터 월요일이고 6이 토요일이다.
     ***********************************************************************************************/

    private final SchedulerService schedulerService;

    @Scheduled(cron = "0 0 0/1 * * *")
    public void setLostItem() {
        log.info("##### setLostItem Start #####");
        schedulerService.setLostItem();
        log.info("##### setLostItem End #####");
    }
}
