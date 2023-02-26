package project.toy.api.scheduler.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import project.toy.api.domain.LostItem;
import project.toy.api.domain.LostStatus;
import project.toy.api.domain.MemberLostItem;
import project.toy.api.repository.LostItemRepository;
import project.toy.api.repository.MemberLostItemRepository;
import project.toy.api.repository.MemberLostItemRepositoryCustom;
import project.toy.api.scheduler.service.SchedulerService;
import project.toy.api.scheduler.service.SendMail;
import project.toy.api.scheduler.vo.SendMailVO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    private final LostItemService lostItemService;
    private final MemberLostItemRepository memberLostItemRepository;

    private final SendMail sendMail;


//    @Scheduled(cron = "0 0 0/1 * * *")
//    public void setLostItem() {
//        log.info("##### setLostItem Start #####");
//        schedulerService.setLostItem();
//        log.info("##### setLostItem End #####");
//    }

    @Scheduled(cron = "0 0 0/1 * * *")
    public void getLostItem(){
        int sendCount = 0;
        List<MemberLostItem> memberLostItems = memberLostItemRepository.findMemberLostItems();
        List<SendMailVO> mails =  memberLostItems.stream().flatMap(memberItem ->
                lostItemService.findLostItem(memberItem).stream().map(item -> {
                    SendMailVO sendMailVO = new SendMailVO();
                    sendMailVO.setEmail(memberItem.getMember().getEmail());
                    sendMailVO.setItemName(item.getItemName());
                    sendMailVO.setCategory(item.getCategory());
                    sendMailVO.setItemDetailInfo(item.getItemDetailInfo());
                    return sendMailVO;
                })).collect(Collectors.toList());

        if(!ObjectUtils.isEmpty(mails)){
            for (SendMailVO mail : mails) {
                sendCount += sendMail.send(mail);
            }
        }
        System.out.println("이메일이 총 " + sendCount + "건 발송 되었습니다.");
    }
}
