package com.example.PerrysChatApp.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.UNAUTHORIZED)
public class InvalidEditException extends RuntimeException {
    private static final String ERROR_MESSAGE = "Unable to edit message. ";
    public InvalidEditException(String message) {
        super(ERROR_MESSAGE + message);
    }
}
