package com.alamer.jctradeexporter.service;

import com.alamer.jctradeexporter.configuration.MailConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.file.Path;
import java.util.List;

@Service
public class MailSenderService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    MailConfig mailConfig;


    public void sendFilesToDrom(List<Path> filesToSend) throws MessagingException {
        for(Path p:filesToSend) {
            String fileName = p.getFileName().toString();
            String toEmail = mailConfig.getToEmailMap().get(fileName);
            sendEmail(toEmail,"Прайс Титан","",p);

        }
    }

    private void sendEmail(String to, String subject, String body, Path... attachmentList) throws MessagingException {

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(mailConfig.getFromEmail());
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body);

        for (Path f : attachmentList) {
            FileSystemResource file = new FileSystemResource(f);
            helper.addAttachment(file.getFilename(), file);

        }
        javaMailSender.send(message);


    }

}
