package com.example.adClear.adClear.controller.dto;

import com.example.adClear.adClear.service.data.StatisticRequestData;

public class StatisticRequestDtoUtil {

    public static HourlyStatisticsRequestDto toStatisticRequestDto(StatisticRequestData data) {
        HourlyStatisticsRequestDto hourlyStatisticsRequestDto = new HourlyStatisticsRequestDto();
        hourlyStatisticsRequestDto.setDate(data.getDate());
        return hourlyStatisticsRequestDto;

    }

    public static StatisticRequestData toClientRequestData(HourlyStatisticsRequestDto dto) {
        return StatisticRequestData.builder().date(dto.getDate()).build();

    }
}
