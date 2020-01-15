package com.kopivad.testingsystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;

@Configuration
public class ThymeleafConfig {
    @Bean
    public SpringSecurityDialect springTemplateEngine() {
        return new SpringSecurityDialect ();
    }
}
