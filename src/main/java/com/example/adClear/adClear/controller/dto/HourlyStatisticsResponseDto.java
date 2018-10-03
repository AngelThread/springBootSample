package com.example.adClear.adClear.controller.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class HourlyStatisticsResponseDto {

    private long totalRequestCount;
    private long customerID;
    private LocalDate date;


}
