package com.example.adClear.adClear.controller;

import com.example.adClear.adClear.controller.dto.ClientRequestDto;
import com.example.adClear.adClear.exception.InvalidRequestException;
import com.example.adClear.adClear.service.HourStatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
public class MainController {
    @Autowired
    private HourStatService hourStatService;

    //TODO add client id to READ.me
    @PostMapping("/clients/{clientId}/request")
    @ResponseBody
    public ResponseEntity<Object> clientRequest(@Valid @RequestBody ClientRequestDto object, @PathVariable long clientId, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.debug("BindingResult belongs to client:" + clientId);
            throw new InvalidRequestException("Client:" + clientId + " send invalid request");
        }
        hourStatService.handleClientRequest(object);

        return new ResponseEntity<>(object, HttpStatus.OK);
    }
}
