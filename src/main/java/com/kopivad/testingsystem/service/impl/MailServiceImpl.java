package com.kopivad.testingsystem.service.impl;

import com.kopivad.testingsystem.domain.Mail;
import com.kopivad.testingsystem.service.MailService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
@Log4j2
public class MailServiceImpl implements MailService {
    public final JavaMailSender emailSender;
    public final RabbitTemplate rabbitTemplate;

    @Override
    @RabbitListener(queues = "mail")
    public void sendMessage(Mail mail) {
        log.info("Mail received {}", mail.getReceiver());
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mail.getReceiver());
        message.setSubject(mail.getSubject());
        message.setText(mail.getText());
        System.out.println(message.toString());
        emailSender.send(message);
    }
}
