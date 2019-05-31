package com.alamer.jctradeexporter.service;

import com.alamer.jctradeexporter.configuration.ApplicationConfig;
import com.alamer.jctradeexporter.dao.AutoshopImageDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

@Service
public class ExportImageService {

    Logger logger = LoggerFactory.getLogger(ExportImageService.class);

    final
    ApplicationConfig config;

    final
    DataSource dataSource;

    @Autowired
    AutoshopImageDAO imageDAO;

    @Value("file:sql/image.sql")
    Resource resourceFile;

    public ExportImageService(ApplicationConfig config, DataSource dataSource) {
        this.config = config;
        this.dataSource = dataSource;
    }


    public void exportImages() throws Exception {
        File dir=new File(config.getOutputImageDirectory());
        String sql = new String(Files.readAllBytes(resourceFile.getFile().toPath()));
        //List<AutoShopImageDTO> allNew = imageDAO.findAllNew();
        try (Connection con = dataSource.getConnection()) {
            try (Statement st = con.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY,
                    java.sql.ResultSet.CONCUR_READ_ONLY)) {
                ResultSet rs = st.executeQuery(sql);
                while (rs.next()) {
                    String fileName = rs.getString(1) + "_" + rs.getString(2) + ".jpg";
                    logger.info(fileName);
                    File image = new File(config.getOutputImageDirectory() + "/" + fileName);
                    try (FileOutputStream fos = new FileOutputStream(image);
                         InputStream is = rs.getBinaryStream(3)) {
                        byte[] buffer = new byte[1024 * 300];
                        while (is.read(buffer) > 0) {
                            fos.write(buffer);
                        }
                        logger.info("Write done");
                    }
                }
            } catch (Exception e) {
                con.rollback();
                throw e;
            }
        }
    }


}
