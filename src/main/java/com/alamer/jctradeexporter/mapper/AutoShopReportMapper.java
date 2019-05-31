package com.alamer.jctradeexporter.mapper;

import com.alamer.jctradeexporter.dto.AutoShopReportDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AutoShopReportMapper implements RowMapper<AutoShopReportDTO> {
    @Override
    public AutoShopReportDTO mapRow(ResultSet rs, int i) throws SQLException {
        return AutoShopReportDTO.builder()
                .id(rs.getString("barcode"))
                .name(rs.getString("name"))
                .mark(rs.getString("field1"))
                .model(rs.getString("field2"))
                .body(rs.getString("field3"))
                .number(rs.getString("field4"))
                .engine(rs.getString("field5"))
                .leftRight(rs.getString("field7"))
                .frontRear(rs.getString("field6"))
                .upDown(rs.getString("field8"))
                .date(rs.getString("field13"))
                .photo1(rs.getString("photo_1"))
                .photo2(rs.getString("photo_2"))
                .photo3(rs.getString("photo_3"))
                .photo4(rs.getString("photo_4"))
                .price(rs.getString("PRICE_REC_RUB"))
                .note(rs.getString("REMARK"))
                .year(rs.getString("FIELD13"))
                .color(rs.getString("FIELD12"))
                .oemNum(rs.getString("oem_code"))
                .priceNum(rs.getString("price_num"))
                .build();
    }
}
