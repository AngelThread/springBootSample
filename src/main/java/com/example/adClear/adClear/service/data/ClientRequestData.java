package com.example.adClear.adClear.service.data;

import lombok.Builder;
import lombok.Value;

import java.sql.Timestamp;

@Value
@Builder
public class ClientRequestData {
    private long customerID;
    private int tagID;
    private String userID;
    private String remoteIP;
    private Timestamp timestamp;

}
