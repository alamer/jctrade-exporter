package com.alamer.jctradeexporter.service;

import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class XlsPathService {


    public Path getPath(String baseDir) {
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String currentFolder = date.format(formatter);
        Path p = Paths.get(baseDir + "/" + currentFolder);
        p.toFile().mkdirs();
        return p;
    }

    public void cleanPath(String baseDir) {
        Path path = getPath(baseDir);
        FileSystemUtils.deleteRecursively(path.toFile());
    }
}
