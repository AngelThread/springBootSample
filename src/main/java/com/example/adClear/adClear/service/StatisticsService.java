package com.example.adClear.adClear.service;

import com.example.adClear.adClear.controller.dto.HourlyStatisticsRequestDto;
import com.example.adClear.adClear.controller.dto.HourlyStatisticsResponseDto;

import javax.validation.Valid;
import java.util.Optional;

public interface StatisticsService {
    Optional<HourlyStatisticsResponseDto> fetchHourlyStatistics(@Valid long clientId, @Valid HourlyStatisticsRequestDto request);

}
