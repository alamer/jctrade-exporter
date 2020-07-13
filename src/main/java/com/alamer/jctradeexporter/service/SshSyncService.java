package com.alamer.jctradeexporter.service;

import com.alamer.jctradeexporter.configuration.ApplicationConfig;
import com.alamer.jctradeexporter.configuration.RemoteHostConfig;
import com.jcraft.jsch.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

@Service
public class SshSyncService {
    Logger logger = LoggerFactory.getLogger(SshSyncService.class);


    final
    ApplicationConfig config;

    final
    RemoteHostConfig remoteHostConfig;

    final
    XlsPathService xlsPathService;

    public SshSyncService(ApplicationConfig config, RemoteHostConfig remoteHostConfig, XlsPathService xlsPathService) {
        this.config = config;
        this.remoteHostConfig = remoteHostConfig;
        this.xlsPathService = xlsPathService;
    }

    public void syncFolderWithRemote() throws JSchException, SftpException, IOException {

        logger.info("Connect ...");
        Session session = startSession();
        logger.info("Open sftp channel");
        Channel channel = session.openChannel("sftp");
        channel.connect();
        ChannelSftp channelSftp = (ChannelSftp) channel;
        logger.info("Navigate to " + remoteHostConfig.getPath());
        channelSftp.cd(remoteHostConfig.getPath());
        logger.info("Read local directory");
        File dir = new File(config.getOutputImageDirectory());
        File[] fileList = dir.listFiles();
        logger.info(String.format("%d files to go", fileList.length));
        int cnt = 0;
        for (File f :fileList) {
            try (FileInputStream fos = new FileInputStream(f)) {
                channelSftp.put(fos, f.getName(), ChannelSftp.OVERWRITE);
                logger.info(String.format("%d of %d done", ++cnt, fileList.length));
            }
        }
        logger.info("Sync  XLS files... ");
        Path savePath = xlsPathService.getPath(config.getOutputReportDir());
        logger.info(savePath.toString());
        Files.walk(savePath).filter(Files::isRegularFile)
                .forEach(f -> {
                    logger.info(f.toString());
                    try (FileInputStream fos = new FileInputStream(f.toFile())) {
                        channelSftp.put(fos, f.toFile().getName(), ChannelSftp.OVERWRITE);
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                });
        channelSftp.exit();
        session.disconnect();

    }


    private Session startSession() throws JSchException {
        JSch jSch = new JSch();
        Session session = jSch.getSession(remoteHostConfig.getLogin(), remoteHostConfig.getHost(), remoteHostConfig.getPort());
        String privateKey = remoteHostConfig.getKey();
        jSch.addIdentity(privateKey);

        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);
        session.connect();
        return session;
    }
}
