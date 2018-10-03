package com.example.adClear.adClear.controller.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class HourlyStatisticsRequestDto {

    private long customerID;
    private long requestCount;
    private LocalDate date;

}
