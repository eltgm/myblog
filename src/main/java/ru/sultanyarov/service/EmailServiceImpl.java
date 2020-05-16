package ru.sultanyarov.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.sultanyarov.models.Email;

@Async
@Service
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender javaMailSender;

    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendEmail(Email email) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo("freestyle1448@gmail.com");
        msg.setFrom(email.getSenderMail());

        msg.setSubject(email.getSubject());
        msg.setText(email.getMessage());

        javaMailSender.send(msg);
    }
}
