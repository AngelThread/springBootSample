package com.example.service;

import com.example.controller.dto.HourlyStatisticsRequestDto;
import com.example.controller.dto.HourlyStatisticsResponseDto;

import javax.validation.Valid;
import java.util.Optional;

public interface StatisticsService {
    Optional<HourlyStatisticsResponseDto> fetchHourlyStatistics(@Valid long clientId, @Valid HourlyStatisticsRequestDto request);

}
