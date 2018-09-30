package com.example.adClear.adClear.controller.dto;

import com.example.adClear.adClear.service.data.ClientRequestData;

public class ClientRequestDtoUtil {

    public static ClientRequestDto toClientRequestDto(ClientRequestData data) {

        return ClientRequestDto.builder().customerID(data.getCustomerID()).remoteIP(data.getRemoteIP()).tagID(data.getTagID())
                .timestamp(data.getTimestamp()).customerID(data.getCustomerID()).build();

    }

    public static ClientRequestData toClientRequestData(ClientRequestDto dto) {

        return ClientRequestData.builder().customerID(dto.getCustomerID()).remoteIP(dto.getRemoteIP()).tagID(dto.getTagID())
                .timestamp(dto.getTimestamp()).customerID(dto.getCustomerID()).build();

    }
}
