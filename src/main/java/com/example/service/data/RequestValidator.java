package com.example.service.data;

import com.example.controller.dto.ClientRequestDto;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
public class RequestValidator {

    public static boolean validate(Object sentObject) {
        if (sentObject == null) {
            return false;
        }
        try {
            ClientRequestDto clientRequestDto = (ClientRequestDto) sentObject;
            return checkIfFieldsNull(clientRequestDto);

        } catch (ClassCastException exp) {
            log.debug("Invalid request body!", exp);
        }
        return false;
    }

    private static boolean checkIfFieldsNull(ClientRequestDto sentObject) {

        return Objects.nonNull(sentObject.getRemoteIP())
                && Objects.nonNull(sentObject.getUserID()) && Objects.nonNull(sentObject.getTimestamp());

    }
}
