package com.alamer.jctradeexporter.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class MailConfig {

    @Value("${mail.from}")
    @Getter
    String fromEmail;


    @Value("#{${mail.to}}")
    @Getter
    private Map<String,String> toEmailMap;

}
