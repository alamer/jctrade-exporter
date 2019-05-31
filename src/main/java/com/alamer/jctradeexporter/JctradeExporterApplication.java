package com.alamer.jctradeexporter;

import com.alamer.jctradeexporter.service.ExportImageService;
import com.alamer.jctradeexporter.service.ExportXlsService;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static java.lang.System.exit;

@SpringBootApplication
public class JctradeExporterApplication implements CommandLineRunner {


    final
    ExportXlsService xlsService;

    final
    ExportImageService imageService;

    public JctradeExporterApplication(ExportXlsService xlsService, ExportImageService imageService) {
        this.xlsService = xlsService;
        this.imageService = imageService;
    }

    public static void main(String[] args) {
        //disabled banner, don't want to see the spring logo
        SpringApplication app = new SpringApplication(JctradeExporterApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);

    }
    // Put your logic here.
    @Override
    public void run(String... args){


        exit(0);
    }


}
