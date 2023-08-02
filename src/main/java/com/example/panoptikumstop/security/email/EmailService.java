package com.example.panoptikumstop.security.email;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Service
@AllArgsConstructor
@Slf4j
public class EmailService implements EmailSender {


    private static final String FAILED_TO_SEND_EMAIL = "failed to send email";

    private static final String UTF_8 = "utf-8";
    private static final String EMAIL = "mdic.sys@gmail.com";
    private final JavaMailSender mailSender;


    @Override
    @Async
    public void sendRegistrationEmail(String to, String email) {
        try {
            var mimeMessage = mailSender.createMimeMessage();
            var helper =
                    new MimeMessageHelper(mimeMessage, UTF_8);
            helper.setText(email, true);
            helper.setTo(to);
            helper.setSubject("Welcome by PanoptikumStop");
            helper.setFrom(EMAIL);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            log.error(FAILED_TO_SEND_EMAIL, e);
            throw new IllegalStateException(FAILED_TO_SEND_EMAIL);
        }


    }

    @Override
    @Async
    public void sendResetPasswordEmail(String to, String email) {
        try {
            var mimeMessage = mailSender.createMimeMessage();
            var helper =
                    new MimeMessageHelper(mimeMessage, UTF_8);
            helper.setText(email, true);
            helper.setTo(to);
            helper.setSubject("Reset Password");
            helper.setFrom(EMAIL);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            log.error(FAILED_TO_SEND_EMAIL, e);
            throw new IllegalStateException(FAILED_TO_SEND_EMAIL);
        }
    }

    @Override
    @Async
    public void InfoEmail(String to, String email) {
        try {
            var mimeMessage = mailSender.createMimeMessage();
            var helper =
                    new MimeMessageHelper(mimeMessage, UTF_8);
            helper.setText(email, true);
            helper.setTo(to);
            helper.setSubject("Infos ");
            helper.setFrom(EMAIL);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            log.error(FAILED_TO_SEND_EMAIL, e);
            throw new IllegalStateException(FAILED_TO_SEND_EMAIL);
        }
    }
}
