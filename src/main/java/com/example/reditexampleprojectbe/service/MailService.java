package com.example.reditexampleprojectbe.service;


import com.example.reditexampleprojectbe.exception.SpringRedditException;
import com.example.reditexampleprojectbe.model.NotificationEmail;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class MailService {


    private final JavaMailSender mailSender;
    private final MailContentBuilder mailContentBuilder;

    @Async
    public void sendMail(NotificationEmail notificationEmail){
        // interface cung cấp phương thức callback được gọi khi chuẩn bị
        // một tin nhắn MIME
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            // hỗ trợ image, và các tệp đính kèm, và tạp ra các
            // tin nhắn kiểu HTMl
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setFrom("springreddit@email.com");
            mimeMessageHelper.setTo(notificationEmail.getRecipient());
            mimeMessageHelper.setSubject(notificationEmail.getSubject());
            mimeMessageHelper.setText(mailContentBuilder.build(notificationEmail.getBody()));
        };

        try {
            mailSender.send(messagePreparator);
            log.info("Activation email sent!!");
        }catch (MailException e){
            throw new SpringRedditException("Exception occurred when sending mail to " + notificationEmail.getRecipient());
        }
    }
}
