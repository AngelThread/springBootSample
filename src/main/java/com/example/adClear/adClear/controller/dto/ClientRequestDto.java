package com.example.adClear.adClear.controller.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Data
public class ClientRequestDto {
    @NotNull
    private long customerID;
    @NotNull
    private int tagID;
    @NotNull
    private String userID;
    @NotNull
    private String remoteIP;
    @NotNull
    private String timestamp;

}
