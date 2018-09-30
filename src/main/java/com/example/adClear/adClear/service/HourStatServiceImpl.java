package com.example.adClear.adClear.service;

import com.example.adClear.adClear.controller.dto.ClientRequestDto;
import com.example.adClear.adClear.dao.HourlyStatsDAO;
import com.example.adClear.adClear.entity.HourlyStat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class HourStatServiceImpl implements HourStatService {
    @Autowired
    private HourlyStatsDAO hourlyStatsDAO;

    public HourlyStat handleClientRequest(ClientRequestDto sentObject) {


        return null;
    }

    @Override
    public void addInvalidRequestToStats(long clientId, Timestamp timestamp) {
        hourlyStatsDAO.findByStatsOfTheHour(clientId,timestamp);
    }

}
