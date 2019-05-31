package com.alamer.jctradeexporter.service;

import com.alamer.jctradeexporter.dao.AutoshopReportDAO;
import com.alamer.jctradeexporter.dto.AutoShopReportDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ExportXlsServiceTest {

    @MockBean
    AutoshopReportDAO reportDAO;

    @Autowired
    ExportXlsService exportXlsService;


    @Test
    public void testXlsGeneration() throws IOException {
        List<AutoShopReportDTO> list= Arrays.asList(
                AutoShopReportDTO.builder().priceNum("export_titan1.xls").id("1").build(),
                AutoShopReportDTO.builder().priceNum("export_titan1.xls").id("3").build(),
                AutoShopReportDTO.builder().priceNum("export_titan1.xls").id("5").build(),
                AutoShopReportDTO.builder().priceNum("export_titan2.xls").id("2").build(),
                AutoShopReportDTO.builder().priceNum("export_titan3.xls").id("4").build());
        Mockito.when(reportDAO.findAll()).thenReturn(list);
        exportXlsService.exportToXls();
    }
}