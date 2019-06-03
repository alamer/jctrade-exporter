package com.alamer.jctradeexporter.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration

public class RemoteHostConfig {

    @Value("${autoshop.remote.host}")
    @Getter
    String host;

    @Value("${autoshop.remote.port}")
    @Getter
    int port;

    @Value("${autoshop.remote.login}")
    @Getter
    String login;

    @Value("${autoshop.remote.path}")
    @Getter
    String path;

    @Value("${autoshop.remote.key}")
    @Getter
    String key;

}
