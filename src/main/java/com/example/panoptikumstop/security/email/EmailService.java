package com.example.panoptikumstop.security.email;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Service
@AllArgsConstructor
public class EmailService implements EmailSender {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(EmailService.class);
    private static final String FAILED_TO_SEND_EMAIL = "failed to send email";

    private final JavaMailSender mailSender;


    @Override
    @Async
    public void sendRegistrationEmail(String to, String email) {
        try {
            var mimeMessage = mailSender.createMimeMessage();
            var helper =
                    new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(email, true);
            helper.setTo(to);
            helper.setSubject("Welcome by PanoptikumStop");
            helper.setFrom("mdic.sys@gmail.com");
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            LOGGER.error(FAILED_TO_SEND_EMAIL, e);
            throw new IllegalStateException(FAILED_TO_SEND_EMAIL);
        }


    }

    @Override
    @Async
    public void sendResetPasswordEmail(String to, String email) {
        try {
            var mimeMessage = mailSender.createMimeMessage();
            var helper =
                    new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(email, true);
            helper.setTo(to);
            helper.setSubject("Reset Password");
            helper.setFrom("mdic.sys@gmail.com");
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            LOGGER.error(FAILED_TO_SEND_EMAIL, e);
            throw new IllegalStateException(FAILED_TO_SEND_EMAIL);
        }
    }

    @Override
    @Async
    public void InfoEmail(String to, String email) {
        try {
            var mimeMessage = mailSender.createMimeMessage();
            var helper =
                    new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(email, true);
            helper.setTo(to);
            helper.setSubject("Infos ");
            helper.setFrom("mdic.sys@gmail.com");
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            LOGGER.error(FAILED_TO_SEND_EMAIL, e);
            throw new IllegalStateException(FAILED_TO_SEND_EMAIL);
        }
    }
}
