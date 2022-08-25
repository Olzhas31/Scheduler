package com.example.Scheduler.exceptions;

public class RestAPIException extends RuntimeException {
    public RestAPIException(String message) {
        super(message);
    }
}
