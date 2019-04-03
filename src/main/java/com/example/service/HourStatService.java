package com.example.service;

import com.example.service.data.ClientRequestData;

import java.sql.Timestamp;
import java.util.Optional;

public interface HourStatService {

    Optional<ClientRequestData> handleClientRequest(long clientId, ClientRequestData sentObject);

    void addInvalidRequestToStats(long aLong, Timestamp timestamp);

}
