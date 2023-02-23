package project.toy.api.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import project.toy.api.domain.LostCategory;
import project.toy.api.domain.LostStatus;
import project.toy.api.scheduler.api.Scheduler;
import project.toy.api.scheduler.service.SendMail;
import project.toy.api.scheduler.vo.SendMailVo;

import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
public class SendMailTest {

    @Autowired
    SendMail sendMail;

    @Autowired
    Scheduler scheduler;

    @Test
    @DisplayName("메일 보내기")
    public void sendMail(){
        //given
        SendMailVo mailVO = new SendMailVo();
        mailVO.setEmail("hk.kim.dev01@gmail.com");
        mailVO.setStatus(LostStatus.KEEP);
        mailVO.setCategory(LostCategory.BACKPACK);
        mailVO.setItemName("게임기");
        mailVO.setItemDetailInfo("닌텐도 DS 게임기 인거 같아요");
        //when
        sendMail.send(mailVO);
    }

    @Test
    @DisplayName("분실물 유저 매칭 스케쥴러")
    public void matchMemberLostItem() {
        //given

        //when
        scheduler.getLostItem();

        //then
    }
}

