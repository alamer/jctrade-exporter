package com.alamer.jctradeexporter.mapper;

import com.alamer.jctradeexporter.dto.AutoShopImageDTO;
import org.springframework.jdbc.core.RowMapper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AutoShopImageMapper implements RowMapper<AutoShopImageDTO> {
    @Override
    public AutoShopImageDTO mapRow(ResultSet rs, int i) throws SQLException {

        String fileName = rs.getString(1) + "_" + rs.getString(2) + ".jpg";
        File image = new File("out/" + fileName);
        try (FileOutputStream fos = new FileOutputStream(image);
             InputStream is = rs.getBinaryStream(3)) {
            byte[] buffer = new byte[1024 * 300];
            while (is.read(buffer) > 0) {
                fos.write(buffer);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return AutoShopImageDTO.builder()
                .name(fileName)
                .file(image)
                .build();
    }
}
