package com.example.adClear.adClear.controller.dto;

import com.example.adClear.adClear.service.data.ClientRequestData;

public class ClientRequestDtoUtil {

    public static ClientRequestDto toClientRequestDto(ClientRequestData data) {
        ClientRequestDto clientRequestDto = new ClientRequestDto();
        clientRequestDto.setCustomerID(data.getCustomerID());
        clientRequestDto.setRemoteIP(data.getRemoteIP());
        clientRequestDto.setTagID(data.getTagID());
        clientRequestDto.setUserID(data.getUserID());
        clientRequestDto.setTimestamp(data.getTimestamp());
        return clientRequestDto;

    }

    public static ClientRequestData toClientRequestData(ClientRequestDto dto) {

        return ClientRequestData.builder().customerID(dto.getCustomerID()).remoteIP(dto.getRemoteIP()).tagID(dto.getTagID())
                .timestamp(dto.getTimestamp()).customerID(dto.getCustomerID()).build();

    }
}
