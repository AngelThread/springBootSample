package com.example.adClear.adClear.service.data;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class StatisticRequestData {
    private LocalDate date;
    private long customerID;

}
