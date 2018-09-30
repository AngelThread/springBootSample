package com.example.adClear.adClear.service.data;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ClientRequestData {
    private long customerID;
    private int tagID;
    private String userID;
    private String remoteIP;
    private String timestamp;

}
