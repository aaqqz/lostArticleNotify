package project.toy.api.scheduler.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.util.ToStringUtil;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import project.toy.api.scheduler.vo.SendMailVo;

@Slf4j
@Service
@RequiredArgsConstructor
public class SendMail {
    private final JavaMailSender mailSender;

    public int send(SendMailVo mail) {
        int result = 0;
        try {
            System.out.println("mail = " + mail);

            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            // 1. 메일 수신자 설정
            String receiveList = mail.getEmail();

            simpleMailMessage.setTo(receiveList);

            // 2. 메일 제목 설정
            simpleMailMessage.setSubject("[분실물 매칭] 분실물 " + mail.getItemName()+ "이 매칭되었습니다.");

            // 3. 메일 내용 설정
            simpleMailMessage.setText(mail.getItemDetailInfo() + mail.getStatus());

            // 4. 메일 전송
            JavaMailSender mailSender1 = mailSender;
            mailSender1.send(simpleMailMessage);

            result = 1;
        } catch (MailException e) {
            throw new RuntimeException(e);
        }

        return result;
    }
}
