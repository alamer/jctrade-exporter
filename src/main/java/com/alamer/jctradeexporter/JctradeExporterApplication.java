package com.alamer.jctradeexporter;

import com.alamer.jctradeexporter.service.ExportImageService;
import com.alamer.jctradeexporter.service.ExportXlsService;
import com.alamer.jctradeexporter.service.MailSenderService;
import com.alamer.jctradeexporter.service.SshSyncService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

import static java.lang.System.exit;

@SpringBootApplication
public class JctradeExporterApplication implements CommandLineRunner {

    Logger logger= LoggerFactory.getLogger(JctradeExporterApplication.class);

    final
    ExportXlsService xlsService;

    final
    ExportImageService imageService;

    final
    SshSyncService syncService;

    @Autowired
    MailSenderService mailSenderService;

    public JctradeExporterApplication(ExportXlsService xlsService, ExportImageService imageService, SshSyncService syncService) {
        this.xlsService = xlsService;
        this.imageService = imageService;
        this.syncService = syncService;
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
            List<Path> reportList = xlsService.exportToXls();
            Instant then = Instant.now();
            Duration duration = Duration.between(now, then);

            logger.info("Done in "+duration.getSeconds()+" sec");
            logger.info("Start image export ..");
            now = Instant.now();
            imageService.exportImages();
            then = Instant.now();
            duration = Duration.between(now, then);
            logger.info("Done in "+duration.getSeconds()+" sec");
            logger.info("Start image sync");
            now = Instant.now();
            syncService.syncFolderWithRemote();
            then = Instant.now();
            duration = Duration.between(now, then);
            logger.info("Done in "+duration.getSeconds()+" sec");

            //mailSenderService.sendFilesToDrom(reportList);
            Runtime.getRuntime().exec("explorer.exe /select, "
                    +reportList.get(0).getParent().toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
        exit(0);
    }


}
