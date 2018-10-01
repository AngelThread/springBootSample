package com.example.adClear.adClear.service;

import com.example.adClear.adClear.service.data.ClientRequestData;

import java.sql.Timestamp;
import java.util.Optional;

public interface HourStatService {

    Optional<Object> handleClientRequest(long clientId, ClientRequestData sentObject);

    void addInvalidRequestToStats(long aLong, Timestamp timestamp);

}
