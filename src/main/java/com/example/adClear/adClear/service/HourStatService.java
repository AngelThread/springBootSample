package com.example.adClear.adClear.service;

import com.example.adClear.adClear.controller.dto.ClientRequestDto;
import com.example.adClear.adClear.entity.HourlyStat;

import java.sql.Timestamp;

public interface HourStatService {

    HourlyStat handleClientRequest(ClientRequestDto sentObject);

    void addInvalidRequestToStats(long aLong, Timestamp timestamp);

}
