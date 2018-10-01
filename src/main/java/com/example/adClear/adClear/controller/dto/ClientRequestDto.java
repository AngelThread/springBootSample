package com.example.adClear.adClear.controller.dto;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

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
    @Pattern(regexp="^([1-9][5-9]\\d{8}\\d*|\\d{11,})$")
    private String timestamp;

}
