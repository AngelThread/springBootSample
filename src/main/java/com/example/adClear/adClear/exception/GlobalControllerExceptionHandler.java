package com.example.adClear.adClear.exception;


import com.example.adClear.adClear.exception.InvalidRequestException;
import com.example.adClear.adClear.service.HourStatService;
import com.fasterxml.jackson.core.JsonParseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class GlobalControllerExceptionHandler {
    @Autowired
    private HourStatService hourStatService;


    @ResponseStatus(HttpStatus.CONFLICT)  // 409
    @ExceptionHandler({JsonParseException.class, InvalidRequestException.class})
    public void handleConflict(HttpServletRequest req, Exception ex) {

        final Map<String, String> pathVariables = (Map<String, String>) req.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        String clientId = pathVariables.get("clientId");

        hourStatService.addInvalidRequestToStats(Long.parseLong(clientId), Timestamp.valueOf(LocalDateTime.now()));
    }
}
