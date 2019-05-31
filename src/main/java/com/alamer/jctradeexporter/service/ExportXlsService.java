package com.alamer.jctradeexporter.service;

import com.alamer.jctradeexporter.configuration.ApplicationConfig;
import com.alamer.jctradeexporter.dao.AutoshopReportDAO;
import com.alamer.jctradeexporter.dto.AutoShopReportDTO;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class ExportXlsService {

    final
    ApplicationConfig config;

    final
    AutoshopReportDAO reportDAO;

    public ExportXlsService(ApplicationConfig config, AutoshopReportDAO reportDAO) {
        this.config = config;
        this.reportDAO = reportDAO;
    }

    public void exportToXls() throws IOException {
        List<AutoShopReportDTO> all = reportDAO.findAll();
        // Теперь сплитим по файлам
        Map<String, List<AutoShopReportDTO>> collect = splitDataByPriceNumColumn(all);
        Path savePath=getPath(config.getOutputReportDir());
        for (String key : collect.keySet()) {
            List<AutoShopReportDTO> reportDTOList = collect.get(key);
            Workbook workbook = generateWorkbookFromReport(reportDTOList);
            File reportFile = new File(savePath.toString()+"/"+key);
            try (FileOutputStream fos = new FileOutputStream(reportFile)) {
                workbook.write(fos);
            }

        }
    }


    private Map<String, List<AutoShopReportDTO>> splitDataByPriceNumColumn(List<AutoShopReportDTO> list) {
        return list.stream().collect(Collectors.groupingBy(AutoShopReportDTO::getPriceNum));
    }

    private Workbook generateWorkbookFromReport(List<AutoShopReportDTO> dataList) throws IOException {
        try (FileInputStream fis = new FileInputStream("shabJcTrade.xls")) {
            Workbook wb = new HSSFWorkbook(fis);
            Sheet sh = wb.getSheet("Прайс");
            int i = 1;
            for (AutoShopReportDTO m : dataList) {
                Row rw = sh.createRow(i);
                writeModelToRow(rw, m);
                i++;
            }
            return wb;
        }
    }

    private Row writeModelToRow(final Row rw, AutoShopReportDTO dto) {

        Cell c = rw.createCell(0);
        c.setCellValue(dto.getId());
        this.writeStringToNextCell(rw, nvl(dto.getName(), ""));
        this.writeStringToNextCell(rw, nvl(dto.getMark(), ""));
        this.writeStringToNextCell(rw, nvl(dto.getModel(), ""));
        this.writeStringToNextCell(rw, nvl(dto.getBody(), ""));
        this.writeStringToNextCell(rw, nvl(dto.getNumber(), ""));
        this.writeStringToNextCell(rw, nvl(dto.getEngine(), ""));
        this.writeStringToNextCell(rw, nvl(dto.getLeftRight(), ""));
        this.writeStringToNextCell(rw, nvl(dto.getFrontRear(), ""));
        this.writeStringToNextCell(rw, nvl(dto.getUpDown(), ""));
        this.writeStringToNextCell(rw, nvl(dto.getDate(), ""));
        this.writeStringToNextCell(rw, nvl(dto.getPhoto1(), ""));
        this.writeStringToNextCell(rw, nvl(dto.getPhoto2(), ""));
        this.writeStringToNextCell(rw, nvl(dto.getPhoto3(), ""));
        this.writeStringToNextCell(rw, nvl(dto.getPhoto4(), ""));
        this.writeStringToNextCell(rw, nvl(dto.getPrice(), ""));
        this.writeStringToNextCell(rw, nvl(dto.getNote(), ""));
        this.writeStringToNextCell(rw, nvl(dto.getYear(), ""));
        this.writeStringToNextCell(rw, nvl(dto.getColor(), ""));
        this.writeStringToNextCell(rw, nvl(dto.getOemNum(), ""));
        return rw;
    }

    private <T> T nvl(T obj, T obj2) {
        return obj == null ? obj2 : obj;
    }

    private void writeStringToNextCell(Row rw, String s) {
        Cell c = rw.createCell(rw.getLastCellNum() + 1);
        c.setCellValue(s);
    }

    private Path getPath(String baseDir) {
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String currentFolder = date.format(formatter);
        Path p = Paths.get(baseDir + "/" + currentFolder);
        p.toFile().mkdirs();
        return p;
    }


}
