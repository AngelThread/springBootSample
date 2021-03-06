package com.example.controller.dto;

import com.example.service.data.ClientRequestData;

import java.sql.Timestamp;

public class ClientRequestDtoUtil {

    public static ClientRequestDto toClientRequestDto(ClientRequestData data) {
        ClientRequestDto clientRequestDto = new ClientRequestDto();
        clientRequestDto.setCustomerID(data.getCustomerID());
        clientRequestDto.setRemoteIP(data.getRemoteIP());
        clientRequestDto.setTagID(data.getTagID());
        clientRequestDto.setUserID(data.getUserID());
        clientRequestDto.setTimestamp(String.valueOf(data.getTimestamp().getTime()));
        return clientRequestDto;

    }

    public static ClientRequestData toClientRequestData(ClientRequestDto dto) {

        return ClientRequestData.builder()
                .customerID(dto.getCustomerID())
                .remoteIP(dto.getRemoteIP())
                .tagID(dto.getTagID())
                .timestamp(new Timestamp(Long.parseLong(dto.getTimestamp())))
                .userID(dto.getUserID())
                .build();

    }
}
