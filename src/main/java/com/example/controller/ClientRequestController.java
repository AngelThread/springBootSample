package com.example.controller;

import com.example.controller.dto.ClientRequestDto;
import com.example.controller.dto.ClientRequestDtoUtil;
import com.example.exception.InvalidRequestException;
import com.example.service.HourStatService;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ClientRequestController {

    @Autowired
    private HourStatService hourStatService;

    @PostMapping("/clients/{clientId}/request")
    @ResponseBody
    public ResponseEntity<Object> clientRequest(@Valid @RequestBody ClientRequestDto request,
        @PathVariable @Valid long clientId, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.debug("BindingResult belongs to client:" + clientId);
            throw new InvalidRequestException("Client:" + clientId + " sent invalid request");
        }
        hourStatService.handleClientRequest(clientId, ClientRequestDtoUtil.toClientRequestData(request));

        return new ResponseEntity<>(request, HttpStatus.OK);
    }
}
