package com.alamer.jctradeexporter.dto;

import lombok.Builder;
import lombok.Getter;

import java.io.File;

@Builder
@Getter
public class AutoShopImageDTO {

    private String name;
    private File file;
}
