package com.example.bankapp.exceptions;

public class CustomRestServiceException extends Exception  {
    public CustomRestServiceException(String message) {
        super(message);
    }
    public CustomRestServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
