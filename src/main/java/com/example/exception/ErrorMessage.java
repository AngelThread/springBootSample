package com.example.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@AllArgsConstructor
@Data
@ToString
public class ErrorMessage {
    private List<String> errors;
    private int status;
}
