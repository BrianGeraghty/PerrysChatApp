package com.example.PerrysChatApp.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST)
public class InvalidMessageException extends RuntimeException {

    private static final String ERROR_MESSAGE = "Unable to send message. ";
    public InvalidMessageException(String message) {
        super(ERROR_MESSAGE + message);
    }
}
