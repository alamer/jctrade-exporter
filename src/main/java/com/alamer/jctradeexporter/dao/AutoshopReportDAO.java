package com.alamer.jctradeexporter.dao;

import com.alamer.jctradeexporter.dto.AutoShopReportDTO;
import com.alamer.jctradeexporter.mapper.AutoShopReportMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Component
public class AutoshopReportDAO {


    @Value("file:sql/report.sql")
    Resource resourceFile;



    final
    JdbcTemplate jdbcTemplate;

    public AutoshopReportDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<AutoShopReportDTO> findAll() throws IOException {
        String sql= new String(Files.readAllBytes(resourceFile.getFile().toPath()));
        Random rand = new Random();
        UUID uuid=UUID.randomUUID();
        sql=sql.replace("{rnd}",uuid.toString());
        return jdbcTemplate.query(sql, new AutoShopReportMapper());
    }
}
