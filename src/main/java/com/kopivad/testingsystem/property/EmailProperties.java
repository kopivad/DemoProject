package com.kopivad.testingsystem.property;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@PropertySource("classpath:email.properties")
@ConfigurationProperties(prefix = "email")
public class EmailProperties {
    private String username;
    private String password;
    private String host;
    private Integer port;
}
