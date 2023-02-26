package project.toy.api.scheduler.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import project.toy.api.scheduler.vo.SendMailVO;

@Slf4j
@Service
@RequiredArgsConstructor
public class SendMail {

    private final JavaMailSender mailSender;

    public void send(SendMailVO mail) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        // 1. 메일 수신자 설정
        simpleMailMessage.setTo(mail.getEmail());

        // 2. 메일 제목 설정
        simpleMailMessage.setSubject("[분실물 매칭] 분실물 " + mail.getItemName() + "이 매칭되었습니다.");

        // 3. 메일 내용 설정
        simpleMailMessage.setText(mail.getStatus() + mail.getTakePosition() + mail.getItemDetailInfo());

        // 4. 메일 전송

        mailSender.send(simpleMailMessage);
    }
}
