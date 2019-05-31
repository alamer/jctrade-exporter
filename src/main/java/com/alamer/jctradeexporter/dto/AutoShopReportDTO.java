package com.alamer.jctradeexporter.dto;

import com.sun.istack.internal.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public final class AutoShopReportDTO {

    @NotNull
    String id;
    String name;
    String mark;
    String model;
    String body;
    String number;
    String engine;
    String leftRight;
    String frontRear;
    String upDown;
    String date;
    String photo1;
    String photo2;
    String photo3;
    String photo4;
    String price;
    String note;
    String year;
    String color;
    String oemNum;
    @NotNull
    String priceNum;

}
