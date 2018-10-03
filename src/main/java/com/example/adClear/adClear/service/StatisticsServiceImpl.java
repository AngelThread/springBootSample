package com.example.adClear.adClear.service;

import com.example.adClear.adClear.controller.dto.HourlyStatisticsRequestDto;
import com.example.adClear.adClear.controller.dto.HourlyStatisticsResponseDto;
import com.example.adClear.adClear.dao.HourlyStatsDAO;
import com.example.adClear.adClear.entity.HourlyStat;
import com.example.adClear.adClear.exception.InvalidStatisticRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.Iterator;
import java.util.Optional;

@Service
public class StatisticsServiceImpl implements StatisticsService {
    @Autowired
    private HourlyStatsDAO hourlyStatsDAO;

    @Autowired
    private CommonValidator commonValidator;


    @Override
    public Optional<HourlyStatisticsResponseDto> fetchHourlyStatistics(@Valid long clientId, @Valid HourlyStatisticsRequestDto request) {
        if (!commonValidator.checkCustomerStatusAndExistenceValid(clientId)) {
            //TODO convert this exception to valid message in response
            throw new InvalidStatisticRequest("There is not active client defined with clientId:" + clientId);
        }
        Iterable<HourlyStat> dailyRequests = hourlyStatsDAO.findDailyRequests(clientId, request.getDate());
        if (dailyRequests == null) {
            return Optional.empty();
        }

        return Optional.ofNullable(buildResponse(clientId, request, dailyRequests));
    }

    private HourlyStatisticsResponseDto buildResponse(@Valid long clientId, @Valid HourlyStatisticsRequestDto request, Iterable<HourlyStat> dailyRequests) {
        Iterator<HourlyStat> iterator = dailyRequests.iterator();
        HourlyStatisticsResponseDto responseDto = new HourlyStatisticsResponseDto();
        //TODO following logic could be designed better
        long totalRequestCount = 0l;

        while (iterator.hasNext()) {
            HourlyStat hourlyStat = iterator.next();
            totalRequestCount += hourlyStat.getRequestCount();
            responseDto.setTotalRequestCount(totalRequestCount);
        }
        responseDto.setCustomerID(clientId);
        responseDto.setDate(request.getDate());
        return responseDto;
    }
}
