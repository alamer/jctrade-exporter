package com.alamer.jctradeexporter;

import com.alamer.jctradeexporter.service.ExportImageService;
import com.alamer.jctradeexporter.service.ExportXlsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

import static java.lang.System.exit;

@SpringBootApplication
public class JctradeExporterApplication implements CommandLineRunner {

    Logger logger= LoggerFactory.getLogger(JctradeExporterApplication.class);

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

        try {
            logger.info("Start xls export ..");
            Instant now = Instant.now();
            xlsService.exportToXls();
            Instant then = Instant.now();
            Duration duration = Duration.between(now, then);

            logger.info("Done in "+duration.getSeconds()+" sec");
            logger.info("Start image export ..");
            now = Instant.now();
            then = Instant.now();
            imageService.exportImages();
            duration = Duration.between(now, then);
            logger.info("Done in "+duration.getSeconds()+" sec");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        exit(0);
    }


}
