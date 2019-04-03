package com.example.exception;

public class InvalidStatisticRequest extends RuntimeException {
    public InvalidStatisticRequest(String message) {
        super(message);
    }
}
