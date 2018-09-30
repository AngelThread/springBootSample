package com.example.adClear.adClear.service;

import com.example.adClear.adClear.controller.dto.ClientRequestDto;
import com.example.adClear.adClear.dao.HourlyStatsDAO;
import com.example.adClear.adClear.entity.HourlyStat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Optional;

@Service
@Slf4j
public class HourStatServiceImpl implements HourStatService {
    @Autowired
    private HourlyStatsDAO hourlyStatsDAO;

    public HourlyStat handleClientRequest(ClientRequestDto sentObject) {


        return null;
    }

    @Override
    public synchronized void addInvalidRequestToStats(long clientId, Timestamp timestamp) {
        Optional<HourlyStat> possibleHourlyStat = hourlyStatsDAO.findByStatsOfTheHour(clientId, timestamp);
        if (possibleHourlyStat.isPresent()) {
            HourlyStat hourlyStat = possibleHourlyStat.get();
            long invalidCount = hourlyStat.getInvalidCount();
            long requestCount = hourlyStat.getRequestCount();
            hourlyStat.setInvalidCount(++invalidCount);
            hourlyStat.setRequestCount(++requestCount);
            hourlyStatsDAO.save(hourlyStat);
            log.debug("Client " + clientId + " invalid request count increased");
        } else {
            HourlyStat hourlyStat = buildHourlyStatAsFirstRecordOfHour(clientId, timestamp);
            hourlyStatsDAO.save(hourlyStat);
            log.debug("Client " + clientId + " new data created for the hour");
        }
    }

    private HourlyStat buildHourlyStatAsFirstRecordOfHour(long clientId, Timestamp timestamp) {
        HourlyStat hourlyStat = new HourlyStat();
        hourlyStat.setRequestCount(1);
        hourlyStat.setInvalidCount(1);
        hourlyStat.setCustomerId(clientId);
        hourlyStat.setTime(timestamp);
        return hourlyStat;
    }

}
