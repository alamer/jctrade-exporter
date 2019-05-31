package com.alamer.jctradeexporter.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {


    @Value("${autoshop.ImageDir}")
    @Getter
    String outputImageDirectory;

    @Value("${autoshop.reportDir}")
    @Getter
    String outputReportDir;
}
