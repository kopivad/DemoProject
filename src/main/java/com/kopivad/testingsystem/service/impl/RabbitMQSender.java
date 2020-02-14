package com.kopivad.testingsystem.service.impl;

import com.kopivad.testingsystem.config.RabbitMQConfig;
import com.kopivad.testingsystem.domain.Mail;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class RabbitMQSender {
    private final RabbitTemplate rabbitTemplate;

    public void send(Mail mail) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY_NAME, mail);
        log.info("Mail sent {}", mail);
    }
}
