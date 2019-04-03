package com.example.exception;


import com.example.controller.ClientRequestController;
import com.example.service.HourStatService;
import com.fasterxml.jackson.core.JsonParseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@ControllerAdvice(assignableTypes = {ClientRequestController.class})

@Slf4j
public class GlobalControllerExceptionHandler {
    @Autowired
    private HourStatService hourStatService;


    @ResponseStatus(HttpStatus.CONFLICT)  // 409
    @ExceptionHandler({JsonParseException.class, InvalidRequestException.class, MethodArgumentNotValidException.class, HttpMessageNotReadableException.class, InvalidBusinessLogicException.class})
    public ResponseEntity<Object> handleRequestsWithError(HttpServletRequest req, Exception ex) {

        String clientId = findClientIdFromRequest(req);


        hourStatService.addInvalidRequestToStats(Long.parseLong(clientId), Timestamp.valueOf(LocalDateTime.now()));

        return new ResponseEntity<>(buildErrorMessage(ex), HttpStatus.BAD_REQUEST);

    }

    private ErrorMessage buildErrorMessage(Exception ex) {

        List<FieldError> fieldErrors = Collections.emptyList();
        List<ObjectError> globalErrors = Collections.emptyList();
        List<String> errors = new ArrayList<>();

        if (ex instanceof MethodArgumentNotValidException) {
            fieldErrors = ((MethodArgumentNotValidException) ex).getBindingResult().getFieldErrors();
            globalErrors = ((MethodArgumentNotValidException) ex).getBindingResult().getGlobalErrors();
        }

        if (ex instanceof InvalidRequestException) {
            errors.add("Invalid Request," + ex.getMessage());
        }

        if (ex instanceof HttpMessageNotReadableException) {
            errors.add("Http Message Not Readable," + "not valid parsable body!");
        }
        if (ex instanceof InvalidBusinessLogicException) {
            errors.addAll(((InvalidBusinessLogicException) ex).getErrors());
        }
        errors = convertValidationErrorsToErrorMessage(fieldErrors, globalErrors, errors);
        return new ErrorMessage(errors, HttpStatus.BAD_REQUEST.value());

    }

    private List<String> convertValidationErrorsToErrorMessage(List<FieldError> fieldErrors, List<ObjectError> globalErrors, List<String> errors) {
        String error;
        for (FieldError fieldError : fieldErrors) {
            error = fieldError.getField() + ", " + fieldError.getDefaultMessage();
            errors.add(error);
        }
        for (ObjectError objectError : globalErrors) {
            error = objectError.getObjectName() + ", " + objectError.getDefaultMessage();
            errors.add(error);
        }
        return errors;
    }

    private String findClientIdFromRequest(HttpServletRequest req) {
        final Map<String, String> pathVariables = (Map<String, String>) req.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        return pathVariables.get("clientId");
    }
}
