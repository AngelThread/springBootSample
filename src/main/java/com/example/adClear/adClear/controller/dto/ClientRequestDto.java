package com.example.adClear.adClear.controller.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ClientRequestDto {
    private long customerID;
    private int tagID;
    private String userID;
    private String remoteIP;
    private String timestamp;

}
