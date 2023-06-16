package com.example.panoptikumstop.security.email;

public interface EmailSender {
    void sendRegistrationEmail(String to, String email);

    void sendResetPasswordEmail(String to, String email);

    void InfoEmail(String to, String email);
}
