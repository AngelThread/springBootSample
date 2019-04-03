package com.example.controller;


import com.example.controller.dto.HourlyStatisticsRequestDto;
import com.example.controller.dto.HourlyStatisticsResponseDto;
import com.example.service.StatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Slf4j
@RestController
public class StatisticsController {
    @Autowired
    private StatisticsService statisticsService;

    @PostMapping("/clients/{clientId}/statistics")
    @ResponseBody
    public ResponseEntity<Object> statisticsRequest(@PathVariable @Valid long clientId, @Valid @RequestBody HourlyStatisticsRequestDto request) {
        Optional<HourlyStatisticsResponseDto> hourlyStatisticsResponseDto = statisticsService.fetchHourlyStatistics(clientId, request);

        if (!hourlyStatisticsResponseDto.isPresent()) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(hourlyStatisticsResponseDto.get(), HttpStatus.OK);
    }
}
