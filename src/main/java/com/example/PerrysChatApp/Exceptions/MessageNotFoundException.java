package com.example.PerrysChatApp.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND)
public class MessageNotFoundException extends RuntimeException {
    private static final String ERROR_MESSAGE = "Could not find message using message id ";
    public MessageNotFoundException(String message) {
        super(ERROR_MESSAGE + message);
    }
}
