package project.toy.api.scheduler.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import project.toy.api.domain.LostCategory;
import project.toy.api.domain.LostStatus;
import project.toy.api.scheduler.vo.SendMailVO;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SendMailTest {

    @Autowired
    SendMail sendMail;

    @Test
    @DisplayName("google email api test")
    void sendMail() {
        SendMailVO sendMailVO = SendMailVO.builder()
                .email("sswwx1@naver.com")
                .status(LostStatus.KEEP)
                .category(LostCategory.TOY)
                .itemName("닌텐도")
                .itemDetailInfo("보관중입니다.")
                .takePosition("영등포 경찰서")
                .build();

        sendMail.send(sendMailVO);
    }
}