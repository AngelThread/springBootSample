package com.example.exception;

import lombok.Getter;

import java.util.List;

public class InvalidBusinessLogicException extends RuntimeException {
    @Getter
    private List<String> errors;

    public InvalidBusinessLogicException(List<String> errors) {
        super(errors.get(0));
        this.errors = errors;
    }
}
