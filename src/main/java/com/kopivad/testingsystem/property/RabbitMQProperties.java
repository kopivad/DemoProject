package com.kopivad.testingsystem.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@PropertySource("classpath:rabbit.properties")
@ConfigurationProperties(prefix = "rabbit")
public class RabbitMQProperties {
    private String queue;
    private String exchange;
    private String routing;
}
