package com.springrest.VulgarityImageAnalyzer.exception;

public class CustomApiException extends RuntimeException {
    public CustomApiException(String message) {
        super(message);
    }
}
