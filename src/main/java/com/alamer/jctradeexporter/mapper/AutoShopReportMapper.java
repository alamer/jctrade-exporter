package com.alamer.jctradeexporter.mapper;

import com.alamer.jctradeexporter.dto.AutoShopReportDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AutoShopReportMapper implements RowMapper<AutoShopReportDTO> {
    @Override
    public AutoShopReportDTO mapRow(ResultSet rs, int i) throws SQLException {
        return AutoShopReportDTO.builder()
                .id(rs.getString(1))
                .name(rs.getString(2))
                .mark(rs.getString(3))
                .model(rs.getString(4))
                .body(rs.getString(5))
                .number(rs.getString(6))
                .engine(rs.getString(7))
                .leftRight(rs.getString(8))
                .frontRear(rs.getString(9))
                .upDown(rs.getString(10))
                .date(rs.getString(11))
                .photo1(rs.getString(12))
                .photo2(rs.getString(13))
                .photo3(rs.getString(14))
                .photo4(rs.getString(15))
                .price(rs.getString(16))
                .note(rs.getString(17))
                .year(rs.getString(18))
                .color(rs.getString(19))
                .oemNum(rs.getString(20))
                .priceNum(rs.getString(21))
                .build();
    }
}
