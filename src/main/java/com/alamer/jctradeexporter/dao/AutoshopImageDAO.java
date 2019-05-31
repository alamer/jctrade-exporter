package com.alamer.jctradeexporter.dao;

import com.alamer.jctradeexporter.dto.AutoShopImageDTO;
import com.alamer.jctradeexporter.mapper.AutoShopImageMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@Component
public class AutoshopImageDAO {

    @Value("file:sql/image.sql")
    Resource resourceFile;

    final
    JdbcTemplate jdbcTemplate;

    public AutoshopImageDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<AutoShopImageDTO> findAllNew() throws IOException {
        String sql= new String(Files.readAllBytes(resourceFile.getFile().toPath()));
        return jdbcTemplate.query(sql,new AutoShopImageMapper());
    }
}
