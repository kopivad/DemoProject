package com.kopivad.testingsystem.service.impl;

import com.kopivad.testingsystem.domain.Mail;
import com.kopivad.testingsystem.property.RabbitMQProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class RabbitMQSender {
    private final RabbitTemplate rabbitTemplate;
    private final RabbitMQProperties rabbitMQProperties;

    public void send(Mail mail) {
        rabbitTemplate.convertAndSend(rabbitMQProperties.getExchange(), rabbitMQProperties.getRouting(), mail);
        log.info("Mail sent {}", mail);
    }
}
