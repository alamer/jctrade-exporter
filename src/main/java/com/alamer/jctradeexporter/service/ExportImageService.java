package com.alamer.jctradeexporter.service;

import com.alamer.jctradeexporter.configuration.ApplicationConfig;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

@Service
public class ExportImageService {


    final
    ApplicationConfig config;

    final
    DataSource dataSource;

    public ExportImageService(ApplicationConfig config, DataSource dataSource) {
        this.config = config;
        this.dataSource = dataSource;
    }


    public void exportImages() throws Exception {
        try (Connection con = dataSource.getConnection()) {
            try(Statement st=con.createStatement();
                //@TODO need SQL only for changed data
                ResultSet rs=st.executeQuery("select 1 from test")) {
                while (rs.next()) {
                    String fileName=rs.getString(1)+"_"+rs.getString(2)+".jpg";
                    File image=new File(config.getOutputImageDirectory()+"/"+fileName);
                    try(FileOutputStream fos=new FileOutputStream(image);
                        InputStream is=rs.getBinaryStream(3)) {
                        byte[] buffer=new byte[1024*300];
                        while (is.read(buffer)>0){
                            fos.write(buffer);
                        }
                    }
                }
            } catch (Exception e) {
                con.rollback();
                throw e;
            }
        }
    }


}
