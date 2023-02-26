package project.toy.api.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import project.toy.api.common.Common;
import project.toy.api.domain.LostCategory;
import project.toy.api.domain.LostStatus;
import project.toy.api.scheduler.api.Scheduler;
import project.toy.api.scheduler.service.SendMail;
import project.toy.api.scheduler.vo.SendMailVO;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class SendMailTest {

    @Autowired
    SendMail sendMail;

    @Autowired
    Scheduler scheduler;

    @Autowired
    CommentService commentService;

    @Test
    @DisplayName("메일 보내기")
    public void sendMail(){
        //given
        SendMailVO mailVO = new SendMailVO();
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


    @Test
    public void getCategory() {
        //when
        LostCategory category = Common.getLostCategory(null);

        //then
//        assertThat(category).isEqualTo(LostCategory.BAG);
        assertThat(category).isEqualTo(LostCategory.ETC);


    }

    @Test
    public void getLostStatus() {

        //when
        LostStatus status = Common.getLostStatus("보관");

        //then
        assertThat(status).isEqualTo(LostStatus.KEEP);
    }
}

